# This will be the common prelude to all of our queries.

import sys     # talk to the operating system
import os.path # manipulate paths to files, directories
import pickle  # read/write pickled python dictionaries
import pprint  # pretty print JSON

data_dir = sys.argv[1] # first command-line argument -- the directory of data

# use os.path.join so that path works on both Windows and Unix
movies_path = os.path.join(data_dir, 'movies.pickled')
people_path = os.path.join(data_dir, 'people.pickled')

# open data dictionary files and un-pickle them
moviesFile = open(movies_path, mode= "rb")
movies     = pickle.load(moviesFile)

peopleFile = open(people_path, mode= "rb")
people     = pickle.load(peopleFile)

#####################################
# write your query code here ...
def getPersonByName (str):
    # initialise output
    the_person = {}
    # iterate through all the keys of the people dictionary
    # looking for one with the right name
    for person_id in people.keys():
        if people[person_id]['name'] == str:
            the_person = people[person_id]
    return the_person

def printCommonMovies(actor1, actor2):
    actorInfo1 = getPersonByName(actor1)
    actorInfo2 = getPersonByName(actor2)
    actorRoles1 = actorInfo1['acted_in']
    actorRoles2 = actorInfo2['acted_in']

    for movie1 in actorRoles1:
        movieTitle1 = movie1['title']
        for movie2 in actorRoles2:
            movieTitle2 = movie2['title']
            if movieTitle1 == movieTitle2:
                print ("%s and %s are coactors in %s" % (actor1, actor2, movieTitle1))

name1 = sys.argv[2] # second command-line argument
name2 = sys.argv[3] # third command-line argument

# ...
printCommonMovies(name1, name2)
