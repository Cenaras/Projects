import os
import discord #https://discordpy.readthedocs.io/en/latest/api.html
from dotenv import load_dotenv
import random
from discord.ext import commands


#Video guide: https://www.youtube.com/watch?v=SPTfmiYiuok&list=WL&index=52&t=186s
# For database: https://www.geeksforgeeks.org/python-mysql-create-database/
# Random Notes: https://realpython.com/how-to-make-a-discord-bot-python/#connecting-a-bot
# List of bots: https://github.com/python-discord/bot/tree/main/bot

load_dotenv()
TOKEN = os.getenv('TOKEN') #Load token from .env file

client = discord.Client() # Get instance of client - this is the connection to discord
bot = commands.Bot(command_prefix='!')

'''
    Ideas:
     - !builds name -> link smite.guru build page
     - !set roles -> store what roles the user wants to play
     - !roles/!god -> return what role/god to play (take roles stored into account) - also allow to specify list from wanted roles
     - !quotes -> display a funny quote said by the members
     - Interaction med Smite API somehow?
     - Gif stuff: Post a random gif of selected favourites (use !addgif to safe favourite gifs and then a link)
    Make functions a map from command name to its function - getattr to do this
        Use this to simplify "message to command" detection (just message \in keys(map) -> call(value)) - Doesn't work cause of arguments!!!
    Help command, display LSH of map from commands
    - !wiki xxx -> osrs wiki
     - !addcommand [name] [video]

'''

quotes = [  "\"Aerodynamik er lidt ligesom pik... Jeg elsker det\" - CandrCry", 
            "\"Hvor fuck er min mur, hvor FUCK er min MUR, HVOR FUCK ER MIN MUR LIGE NU?!!\" - Kasserne",
            "\"I'm not gonna make this...\" - Extranjero",
            "\"Chang'e ult NEDE\" - Bisget"
            
         ]


@client.event
async def on_ready(): # Event triggered when bot is alive
    print('We have logged in as {0.user}'.format(client))

@client.event #Event triggers when a message is received on the server
async def on_message(message):

    msg_content = message.content

    if message.author == client.user: #Event triggers for all messages, so we ignore our own messages
        return
    if msg_content.startswith("!hello"): #We use ! for commands
        #Send the message, wait for it to return
        await message.channel.send((("Hello {}").format(message.author).split("#", 1)[0]) + "!") #Get usrname, remove #xxxx part and append "!" at the end
    
    '''if msg_content.startswith("!quotes"):
        # Allow argument to get quote from specific user
        msg_len = len(msg_content.split(" ")) 
        if msg_len > 1:
            person = msg_content.split(" ")[1]
            # print(person) ONLY SELECT QUOTES FROM THIS PERSON - Check quotes after '-' and only select them...
            print(msg_content)
        await message.channel.send(random.choice(quotes))
    '''


@bot.command(name='quotes', help = "Displays a funny quote from a NuCow member")
async def show_quote(context, *args):
    if len(args) == 0:
        await context.send(random.choice(quotes))

    #Handle lower casing of letters - Regular Expressions maybe?
    else:
        candidate_quotes = []
        person = args[0].lower()

        for cand in quotes:
            if person in cand:
                candidate_quotes.append(cand)
        if len(candidate_quotes) == 0:  #Proper error handling...
            await context.send("No quote from given person...")
        else:
            await context.send(random.choice(candidate_quotes))


bot.run(TOKEN) #Run the bot with the given token - binds our program to our bot
