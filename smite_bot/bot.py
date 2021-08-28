import os
import discord #https://discordpy.readthedocs.io/en/latest/api.html
from dotenv import load_dotenv

load_dotenv()
TOKEN = os.getenv('TOKEN') #Load token from .env file

client = discord.Client() # Get instance of client - this is the connection to discord


'''
    Ideas:
     - !builds name -> link smite.guru build page
     - !set roles -> store what roles the user wants to play
     - !roles/!god -> return what role/god to play (take roles stored into account) - also allow to specify list from wanted roles
     - !quotes -> display a funny quote said by the members
'''

quotes = ["\"Aerodynamik er lidt ligesom pik... Jeg elsker det\" - CandyCry 2017", "\"Hvor fuck er min mur, hvor FUCK er min MUR, HVOR FUCK ER MIN MUR LIGE NU?!!\" - Kasserne 2016"]


@client.event
async def on_ready(): # Event triggered when bot is alive
    print('We have logged in as {0.user}'.format(client))

@client.event #Event triggers when a message is received on the server
async def on_message(message):
    if message.author == client.user: #Event triggers for all messages, so we ignore our own messages
        return
    if message.content.startswith("!hello"): #We use ! for commands
        #Send the message, wait for it to return
        await message.channel.send((("Hello {}").format(message.author).split("#", 1)[0]) + "!") #Get usrname, remove #xxxx part and append "!" at the end
    
    #if message.content



client.run(TOKEN) #Run the client with the given token - binds our program to our bot