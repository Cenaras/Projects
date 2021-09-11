import os
import discord #https://discordpy.readthedocs.io/en/latest/api.html
from dotenv import load_dotenv
import random
from discord.ext import commands
import requests
from requests.models import Response

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
     - !set roles -> store what roles the user wants to play
     - !roles/!god -> return what role/god to play (take roles stored into account) - also allow to specify list from wanted roles

     - Interaction med Smite API somehow?
     - Gif stuff: Post a random gif of selected favourites (use !addgif to safe favourite gifs and then a link)

    Help command, display LSH of map from commands
     - !wiki xxx -> osrs wiki
     - !addcommand [name] [video]

'''

quotes = [  "\"Aerodynamik er lidt ligesom pik... Jeg elsker det\" - CandyCry", 
            "\"Hvor fuck er min mur, hvor FUCK er min MUR, HVOR FUCK ER MIN MUR LIGE NU?!!\" - Kasserne",
            "\"I'm not gonna make this...\" - Extranjero",
            "\"Chang'e ult NEDE\" - Bisget",
            "\"Fucking rat ass racoon dog looking motherfucker\" - Sir Jason Chayse",
            "\"Kasper - Shut the flying!\" - Extranjero", 
            "\"Er der friendly fire? Giv ham lige et lille love-tap bagi\" - Sir Jason Chayse",
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
    
    
### !quotes -> display a funny quote said by the members ###
@bot.command(name='quotes', help = "Displays a funny quote from a NuCow member")
async def show_quote(context, *args):
    if len(args) == 0:
        await context.send(random.choice(quotes))

    #Handle lower casing of letters - Regular Expressions maybe?
    else:
        candidate_quotes = []
        person = args[0].lower()

        for cand in quotes:
            lower_cand = cand.lower()
            if person in lower_cand:
                candidate_quotes.append(cand)
        if len(candidate_quotes) == 0:  #Proper error handling...
            await context.send("No quote from given person...")
        else:
            await context.send(random.choice(candidate_quotes))



### !build name -> link smite.guru build page ###
@bot.command(name='build', help = "Link a SmiteGuru page with a build of the specified god. Specify a god, i.e. '!build rama'")
async def show_builds(context):
    god = context.message.content.split(" ")[1]
    baselink = "https://smite.guru/builds/"
    r = requests.head(baselink+god) #Get request
    if (r.status_code != 200): #The requested god build is not valid on the page - i.e. not status 200 (HTTP OK)
        await context.send("https://smite.guru/builds?search="+god)
    else:
        await context.send(baselink+god) #Else actually display the build page


### !wiki search -> open osrs wiki with term
@bot.command(name='wiki', help = "Search the osrs wiki, with a specific term")
async def display_wiki(context):
    term = ""
    terms = context.message.content.split(" ")
    for i in range (1, len(terms)):
        term = term+terms[i] + "_"

    term = term[:-1]

    baselink = "https://oldschool.runescape.wiki/w/"
    content = requests.get(baselink+term).text
    if ("This page doesn&#039;t exist on the wiki. Maybe it should?" in content):
        await context.send("The requested page does not exist on the old school wiki page")
    else:
        await context.send(baselink+term)
bot.run(TOKEN) #Run the bot with the given token - binds our program to our bot
