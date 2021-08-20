extern crate num;

use num_bigint::BigUint;
use num_bigint::RandBigInt;
use num_traits::{Zero, One};
use num::Integer;
//use std::io::prelude::*;
use core::ops::Sub;
use num_traits::*;

//type PrimePair = (BigUint, BigUint);

/* Since the lib itself (num_primes) gives a wrong type, we simply implement it ourselves using the src code from https://docs.rs/num-primes/0.1.2/src/num_primes/lib.rs.html#89-100*/


pub fn generate_primes(bit_size: usize) -> BigUint
{
    let mut randomizer = rand::thread_rng();
    let mut p: BigUint = randomizer.gen_biguint(bit_size);

    while !is_prime(&p)
    {
        p = randomizer.gen_biguint(bit_size);  
    }
    return p;
}

//We must check that the number indeed is prime... We use the same strategy as https://docs.rs/num-primes/0.1.2/src/num_primes/lib.rs.html#377
//Note: If implementation is wrong, maybe my reimplementations of the above is wrong - then just copy the lib src code

fn is_prime(n: &BigUint) -> bool
{
    //println!("Testing: the number is {}", n);
    if n % BigUint::from(2u64) == BigUint::from(0u64) //If even then not prime
    {
        //println!("Number is even"); 
        return false;
    } 
    
    if !div_small_primes(&n) //If our number is divisible by small primes it's not suitable 
    {
        //println!("Number divisible by small prime"); 
        return false
    } 
    if !fermat_primality_test(&n) 
    {
        //println!("Number failed fermat's primality test"); 
        return false;
    }
    if !miller_rabin(n, 16) 
    {
        //println!("Number failed Miller-Rabin test"); 
        return false;
    }

    return true; //If all tests pass, we believe our number is prime
}

//For performance... Start by dividing by known small primes. If the number divides the primes, then it is not prime... (i.e. 9 % 3 = 0 so 9 is not prime)
fn div_small_primes(n: &BigUint) -> bool
{
    static SMALL_PRIMES_LIST: [u32; 670] = [2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541,547,557,563,569,571,577,587,593,599,601,607,613,617,619,631,641,643,647,653,659,661,673,677,683,691,701,709,719,727,733,739,743,751,757,761,769,773,787,797,809,811,821,823,827,829,839,853,857,859,863,877,881,883,887,907,911,919,929,937,941,947,953,967,971,977,983,991,997,1009,1013,1019,1021,1031,1033,1039,1049,1051,1061,1063,1069,1087,1091,1093,1097,1103,1109,1117,1123,1129,1151,1153,1163,1171,1181,1187,1193,1201,1213,1217,1223,1229,1231,1237,1249,1259,1277,1279,1283,1289,1291,1297,1301,1303,1307,1319,1321,1327,1361,1367,1373,1381,1399,1409,1423,1427,1429,1433,1439,1447,1451,1453,1459,1471,1481,1483,1487,1489,1493,1499,1511,1523,1531,
    1543,1549,1553,1559,1567,1571,1579,1583,1597,1601,1607,1609,1613,1619,1621,1627,1637,1657,1663,1667,1669,1693,1697,1699,1709,1721,1723,1733,1741,1747,1753,1759,1777,1783,1787,1789,1801,1811,1823,1831,1847,1861,1867,1871,1873,1877,1879,1889,1901,1907,1913,1931,1933,1949,1951,1973,1979,1987,1993,1997,1999,2003,2011,2017,2027,2029,2039,2053,2063,2069,2081,2083,2087,2089,2099,2111,2113,2129,2131,2137,2141,2143,2153,2161,2179,2203,2207,2213,2221,2237,2239,2243,2251,2267,2269,2273,2281,2287,2293,2297,2309,2311,2333,2339,2341,2347,2351,2357,2371,2377,2381,2383,2389,2393,2399,2411,2417,2423,2437,2441,2447,2459,2467,2473,2477,2503,2521,2531,2539,2543,2549,2551,2557,2579,2591,2593,2609,2617,2621,2633,2647,2657,2659,2663,2671,2677,2683,2687,2689,2693,2699,2707,2711,2713,2719,2729,2731,2741,2749,2753,2767,2777,2789,2791,2797,2801,2803,2819,2833,2837,2843,2851,2857,2861,2879,2887,2897,2903,2909,2917,2927,2939,2953,2957,2963,2969,2971,2999,3001,3011,3019,3023,3037,3041,3049,3061,3067,3079,3083,3089,3109,3119,3121,3137,3163,3167,3169,3181,3187,3191,
    3203,3209,3217,3221,3229,3251,3253,3257,3259,3271,3299,3301,3307,3313,3319,3323,3329,3331,3343,3347,3359,3361,3371,3373,3389,3391,3407,3413,3433,3449,3457,3461,3463,3467,3469,3491,3499,3511,3517,3527,3529,3533,3539,3541,3547,3557,3559,3571,3581,3583,3593,3607,3613,3617,3623,3631,3637,3643,3659,3671,3673,3677,3691,3697,3701,3709,3719,3727,3733,3739,3761,3767,3769,3779,3793,3797,3803,3821,3823,3833,3847,3851,3853,3863,3877,3881,3889,3907,3911,3917,3919,3923,3929,3931,3943,3947,3967,3989,4001,4003,4007,4013,4019,4021,4027,4049,4051,4057,4073,4079,4091,4093,4099,4111,4127,4129,4133,4139,4153,4157,4159,4177,4201,4211,4217,4219,4229,4231,4241,4243,4253,4259,4261,4271,4273,4283,4289,4297,4327,4337,4339,4349,4357,4363,4373,4391,4397,4409,4421,4423,4441,4447,4451,4457,4463,4481,4483,4493,4507,4513,4517,4519,4523,4547,4549,4561,4567,4583,4591,4597,4603,4621,4637,4639,4643,4649,4651,4657,4663,4673,4679,4691,4703,4721,4723,4729,4733,4751,4759,4783,4787,4789,4793,4799,4801,4813,4817,4831,4861,4871,4877,4889,4903,4909,4919,4931,4933,4937,4943,4951,
    4957,4967,4969,4973,4987,4993,4999,5003];

    for prime in SMALL_PRIMES_LIST.iter()
    {
        if n % &BigUint::from(*prime) == Zero::zero() {return false;}   //If our num % known prime is 0 then our num isn't prime... 
                                                                        // This only works since our p should be larger than 5000... Otherwise it fails (19 % 19 = 0)
    }
    return true;
}

/*
    To check n = 221 is prime, we randomly pick 1 < a < 220, and check if a^(n-1) = 1 mod n
    If it holds, n is prime or a is a Fermat Liar. Then we take another a and test if  a^(n-1) mod n =/= 1
    If such an a exists, so a^(n-1) =/= 1 mod n, n is not prime
*/
fn fermat_primality_test(n: &BigUint) -> bool
{
    let mut rng = rand::thread_rng();
    let random_int = rng.gen_biguint_below(n);

    let exp = n - BigUint::one();
    let fermat_test = random_int.modpow(&exp, n);
    return fermat_test == One::one();

}

//The below functions are copied from the lib - too complicated to understand for now...
fn miller_rabin(candidate: &BigUint, limit: usize) -> bool {
    //println!("Miller Attempt");
    // One and Two in ramp::Int form
    let one = BigUint::one();
    let two = &one + &one;
    
    
    let (d,s) = rewrite(&candidate);

    let mut rng = rand::thread_rng();

    for _i in 0..limit {
        let a = rng.gen_biguint_range(&two, &(candidate-&one));
        let mut x = a.modpow(&d, &candidate);

        if x == one || x == (candidate - &one) {
            continue;
        }
        else {
            // Convert To Usizes For Loop
            let step = s.sub(&one).to_usize().unwrap();
            let one_usize = one.to_usize().unwrap();
            
            for _ in one_usize..step {
                x = x.modpow(&two,candidate);
                if x == BigUint::one() {
                    return false
                } 
                else if x == (candidate - BigUint::one()) {
                    break;
                }
            }
            return false;
        }
    }
    true
}

fn rewrite(n: &BigUint) -> (BigUint,BigUint) {
    let one: BigUint = BigUint::one();
    let two: BigUint = BigUint::one() + BigUint::one();
    let mut i: BigUint = BigUint::zero();
    
    
    

    // (n-1) becomes even number
    let mut d: BigUint = n - &one;

    // The Main Loop That Checks Whether The Number is even and then divides by 2 and stores a counter 
    while d.is_even() == true {
        d /= &two;
        i = i + &one;
    }
    return (d.clone(),i)
}


