import {createStore} from "vuex";
import clubNames from './data/ClubNames.js'
import {ref} from "vue";

export const store = createStore({
    state() {
        return {
            guessList: [],
            clubList: ref(clubNames),
            gameFinished: false,
            stadiumCapacity: 0,
            puzzleDate: "",
            winClub: {
                stadiumName: "",
                city: "",
                league: ""
            }
        }
    },
    mutations: {
        initialiseStore(state) {
            if (localStorage.getItem('fcrdleStore')) {
                store.replaceState(JSON.parse(<string>localStorage.getItem('fcrdleStore')))
            }
        },
        dropStore(state) {
            store.replaceState({
                guessList: [
                    /*{
                        id: "1",
                        name: "Milan",
                        direction: "⬆️",
                        howClose: 1316,
                        stadiumCapacity: 80239,
                        guessCapacityIsLess: false,
                        league: "Serie A",
                        leagueIsCorrect: false
                    }, {
                        id: "2",
                        name: "Rostov",
                        direction: "⬅️️",
                        howClose: 2711,
                        stadiumCapacity: 55300,
                        guessCapacityIsLess: true,
                        league: "Russian Premiere-League",
                        leagueIsCorrect: false
                    }, {
                        id: "3",
                        name: "Manchester United",
                        direction: "⬇️",
                        howClose: 110,
                        stadiumCapacity: 76400,
                        guessCapacityIsLess: false,
                        league: "Premier League",
                        leagueIsCorrect: true
                    }*/
                ],
                clubList: ref(clubNames),
                gameFinished: false,
                stadiumCapacity: 0,
                puzzleDate: "",
                winClub: {
                    name: "",
                    stadiumName: "",
                    city: "",
                    league: "",
                    stadiumCapacity: 0
                }
            })
        },
        addGuess(state, guess) {
            state.guessList.unshift(guess);
        },
        setClubList(state, clubNames) {
            state.clubList = clubNames
        },
        setGameFinished(state, finished) {
            state.gameFinished = finished
        },
        setStadiumCapacity(state, capacity) {
            state.stadiumCapacity = capacity
        },
        setWinClub(state, winClub) {
            state.winClub = winClub
        },
        setPuzzleDate(state, date) {
            state.puzzleDate = date
        }
    }
})

store.subscribe((mutation, state) => {
    localStorage.setItem('fcrdleStore', JSON.stringify(state))
})