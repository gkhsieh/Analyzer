#Framework of program obtained from Farza (Twitter @farzatv)
#Updated to Riot API v3 and .txt file processing done by Jesse Wang
#First we need to import requests. 
import requests

def requestSummonerData(summonerName, APIKey):

    #Here is how I make my URL.  There are many ways to create these.
    
    URL = "https://na1.api.riotgames.com/lol/summoner/v3/summoners/by-name/" + summonerName + "?api_key=" + APIKey
    print URL
    #requests.get is a function given to us my our import "requests". It basically goes to the URL we made and gives us back a JSON.
    response = requests.get(URL)
    #Here I return the JSON we just got.
    return response.json()

def requestRankedData(ID, APIKey):
    URL = "https://na1.api.riotgames.com/lol/league/v3/positions/by-summoner/" + ID + "?api_key=" + APIKey
    print URL
    response = requests.get(URL)
    return response.json()
    

def main():
    #Asks the user two things, their summoner name and API Key.
    #These are the only two things I need from them in order to get create my URL and grab their ID.
    inputFile = open('input.txt', 'r')
    outputFile = open('output.txt', 'w')

    #From txt file and personal API Key
    summonerName1 = (inputFile.readline()).rstrip()
    summonerName2 = (inputFile.readline()).rstrip()
    APIKey = "RGAPI-a93467d9-72af-4720-a459-284f61c7418e"
    
    #summonerName = (str)(raw_input('Type your Summoner Name here and DO NOT INCLUDE ANY SPACES: '))
    #APIKey = (str)(raw_input('Copy and paste your API Key here: '))

    print summonerName1
    print summonerName2
    print APIKey

    #I send these two pieces off to my requestData function which will create the URL and give me back a JSON that has the ID for that specific summoner.
    #Once again, what requestData returns is a JSON.
    responseJSON = requestSummonerData(summonerName1, APIKey)
    
    ID = responseJSON['id']
    ID = str(ID)
    print ID
    responseJSON2 = requestRankedData(ID, APIKey)
    print responseJSON2

    if ((str)(responseJSON2[0]['queueType']) == "RANKED_SOLO_5x5"):
        tier = (str) (responseJSON2[0]['tier'])
        rank = (str) (responseJSON2[0]['rank'])
        wins = (str) (responseJSON2[0]['wins'])
        losses = (str) (responseJSON2[0]['losses'])
    elif ((str)(responseJSON2[1]['queueType']) == "RANKED_SOLO_5x5"):
        tier = (str) (responseJSON2[1]['tier'])
        rank = (str) (responseJSON2[1]['rank'])
        wins = (str) (responseJSON2[1]['wins'])
        losses = (str) (responseJSON2[1]['losses'])
    else:
        tier = (str) (responseJSON2[2]['tier'])
        rank = (str) (responseJSON2[2]['rank'])
        wins = (str) (responseJSON2[2]['wins'])
        losses = (str) (responseJSON2[2]['losses'])
    print tier
    print rank
    print wins
    print losses

    outputFile.write(tier + "\n")
    outputFile.write(rank + "\n")
    outputFile.write(wins + "\n")
    outputFile.write(losses + "\n")
    
    responseJSON = requestSummonerData(summonerName2, APIKey)
    
    ID = responseJSON['id']
    ID = str(ID)
    print ID
    responseJSON2 = requestRankedData(ID, APIKey)
    print responseJSON2

    tier = (str) (responseJSON2[0]['tier'])
    rank = (str) (responseJSON2[0]['rank'])
    wins = (str) (responseJSON2[0]['wins'])
    losses = (str) (responseJSON2[0]['losses'])
    print tier
    print rank 
    print wins
    print losses

    outputFile.write(tier + "\n")
    outputFile.write(rank + "\n")
    outputFile.write(wins + "\n")
    outputFile.write(losses + "\n")

    inputFile.close()
    outputFile.close()    

#This starts my program!
if __name__ == "__main__":
    main()

