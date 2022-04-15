Require Import Decompiler.project_lib.

Inductive unop :=
  | OPred
  | OSucc.

Inductive binop :=
  | OPlus
  | OMinus.

Inductive exp :=
  | ENat (n: nat)
  | EUnOp (op : unop) (e : exp)
  | EBinOp (op : binop) (e1 : exp) (e2 : exp).

(* Evaluating an expression *)
Fixpoint eval (e : exp) : nat :=
  match e with
  | ENat n => n
  | EUnOp op e => 
    match op with
    | OPred => (eval e) - 1
    | OSucc => S (eval e)
    end
  | EBinOp op e1 e2 => let left := eval e1 in let right := eval e2 in match op with
    | OPlus => left + right
    | OMinus => left - right
  end
end.

Definition exp1 := (EUnOp OSucc (ENat 0)).
Definition exp2 := EBinOp (OMinus) (ENat 0) (ENat 3).
Definition exp3 := EBinOp OPlus (ENat 3) (EBinOp OMinus (ENat 2) (ENat 1)).

Compute eval exp1.
Compute eval exp2.
Compute eval exp3.
(*Notation "n" := (ENat n) (in custom decom at level 0).*)

Coercion ENat : nat >-> exp.
Declare Custom Entry decom.
Notation "<{ e }>" := e (e custom decom at level 99).
Notation "( x )" := x (in custom decom, x at level 99).
Notation "x" := x (in custom decom at level 0, x constr at level 0).
Notation "x + y" := (EBinOp OPlus x y) (in custom decom at level 10, left associativity).
Notation "x - y" := (EBinOp OMinus x y) (in custom decom at level 10, left associativity).
Notation " # x " := (EUnOp OSucc x) (in custom decom at level 5).
Notation " % x " := (EUnOp OPred x) (in custom decom at level 5).

Definition test1 := <{ # 0 }>.
Definition test2 := <{ 3 + 2 - 1 }>.
Definition test3 := <{ 1 + 2 - 3 + 4 + (# 2)}>.


Example eval_test1 : eval test1 = 1. Proof. reflexivity. Qed.
Example eval_test2 : eval test2 = 4. Proof. reflexivity. Qed.
Example eval_test3 : eval test3 = 7. Proof. reflexivity. Qed.


(* Instructions for the compiler *)
Inductive instruction :=
  | IPush (n : nat)
  | IUnOp (op : unop)
  | IBinOp (op : binop).

(* The Compiler in Reverse Polish Notation *)
Fixpoint compile (e : exp) : list instruction :=
  match e with
  | ENat n => [ IPush n ]
  | <{ # x }> => compile x ++ [IUnOp OSucc]
  | <{ % x }> => compile x ++ [IUnOp OPred]
  | <{ x + y }> => compile x ++ compile y ++ [IBinOp OPlus]
  | <{ x - y }> => compile x ++ compile y ++ [IBinOp OMinus]
end.


Definition push (n : nat) (stack : list nat) : list nat :=
  match stack with
  | [] => [n]
  | x :: l => [n] ++ [x] ++ l
end.

Fixpoint vm_helper (instructions : list instruction) (stack : list nat) : option nat :=
  match instructions with 
  | nil => match stack with
    | x :: nil => Some x
    | _ => None
  end
  | x :: l => match x with
    | IPush n => (vm_helper l (push n stack) ) 
    | IBinOp op => match op with
      | OPlus => match stack with
        | op1 :: op2 :: s => let res := op2 + op1 in vm_helper l (push res s) (* TOS is 2nd arg *)
        | _ => None
        end
      | OMinus => match stack with
        | op1 :: op2 :: s => let res := op2 - op1 in vm_helper l (push res s)
        | _ => None
        end
      end
    | IUnOp op => match op with
      | OSucc => match stack with
        | op1 :: s => let res := op1 + 1 in vm_helper l (push res s)
        | _ => None
        end
      | OPred => match stack with
        | op1 :: s => let res := op1 - 1 in vm_helper l (push res s)
        | _ => None
        end
      end
    end
end.


(* Virtual Machine for interpreting instructions *)
Definition vm : list instruction -> option nat := fun x : list instruction => vm_helper x [].
Definition neg_test_1 := [IPush 42; IBinOp OPlus].


Example vm_test_1 : vm (compile test2) = Some 4. Proof. reflexivity. Qed.
Example vm_test_2 : vm (compile test3) = Some 7. Proof. reflexivity. Qed.
Example vm_test_3 : vm neg_test_1 = None. Proof. reflexivity. Qed.

(* Need helper lemma. *)




(* Correctness of the compiler - The Virtual Machine always produces some output on compiled code *)
Lemma vm_correct : forall e, vm (compile e) = Some (eval e).
Proof. induction e; simpl. reflexivity.
 - destruct op. apply vm_correct_helper. 
 
 
 apply test. apply IHe. 
