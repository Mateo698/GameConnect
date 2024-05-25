package com.gameconnect.viewmodel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameconnect.domain.model.AppAuthState
import com.gameconnect.model.Game
import com.gameconnect.domain.model.User
import com.gameconnect.repository.AuthRepository
import com.gameconnect.repository.AuthRepositoryImpl
import com.gameconnect.repository.GameRepository
import com.gameconnect.repository.GameRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    val gameRepository: GameRepository = GameRepositoryImpl(),
    val repo: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    val authStatus = MutableLiveData<AppAuthState>()

    val page: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val userEmp = User("","", listOf(), listOf(), listOf(), listOf(), "", "", "","")
    val user: MutableLiveData<User> = MutableLiveData<User>(userEmp)
    var gamesEmp = listOf<Game>()
    val games: MutableLiveData<List<Game>> = MutableLiveData<List<Game>>(gamesEmp)
    val selectedGames: MutableLiveData<List<Game>> = MutableLiveData<List<Game>>(listOf())
    val daysAvailable: MutableLiveData<List<String>> = MutableLiveData<List<String>>(listOf())
    val hoursAvailable: MutableLiveData<String> = MutableLiveData<String>()
    val minutesAvailable: MutableLiveData<String> = MutableLiveData<String>()
    val meridiemAvailable: MutableLiveData<String> = MutableLiveData<String>()

    //Querido Danilo y querido profesor, se que la cantidad de LiveDatas que tengo en este ViewModel es excesiva,
    // pero no me fue como se me ocurrio hacerlo al ser casi que el encargado de hacer el 90% del registro de usuario
    //piedad, por favor
    //Att Mateo Rada :)




    fun nextPage(context: Activity) {
        when (page.value) {
            0 -> {
                //Check if username is empty
                if(user.value?.username.isNullOrEmpty()){
                    Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                }else{
                    page.value = page.value?.plus(1)
                }

            }
            1 -> {
                //Check if genres is empty
                if(user.value?.genres.isNullOrEmpty()) {
                    Toast.makeText(context, "Genres cannot be empty", Toast.LENGTH_SHORT).show()
                    return
                }else{
                    page.value = page.value?.plus(1)
                }
            }
            2 -> {
                //Check if games is empty
                if(selectedGames.value?.isEmpty() == true){
                    Toast.makeText(context, "Games cannot be empty", Toast.LENGTH_SHORT).show()
                    return
                }else{
                    page.value = page.value?.plus(1)
                }
            }
            3 -> {
                //Check if platforms is empty
                if(user.value?.platforms.isNullOrEmpty()){
                    Toast.makeText(context, "Platforms cannot be empty", Toast.LENGTH_SHORT).show()
                    return
                }else{
                    page.value = page.value?.plus(1)
                }
            }
            4 -> {
                //Check if biography is empty
                if(user.value?.biography.isNullOrEmpty()){
                    Toast.makeText(context, "Biography cannot be empty", Toast.LENGTH_SHORT).show()
                    return
                }else{
                    page.value = page.value?.plus(1)
                }
            }
            5 -> {
                //Check if time is empty
                if(!checkTime()){
                    Toast.makeText(context, "Time cannot be empty", Toast.LENGTH_SHORT).show()
                    return
                }else{
                    page.value = page.value?.plus(1)
                }
            }
            6 -> {
                //Check if gamertags is empty
                if(user.value?.gamertags.isNullOrEmpty()){
                    Toast.makeText(context, "Gamertags cannot be empty", Toast.LENGTH_SHORT).show()
                }else{
                    page.value = page.value?.plus(1)
                }
            }
            7 -> {
                //Check if email and password is empty
                if(user.value?.email.isNullOrEmpty() || user.value?.password.isNullOrEmpty()){
                    Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                }else{
                    //Add selected games to user
                    user.value = user.value?.copy(games = selectedGames.value?.map { it.id }!!)
                    //Add time to user
                    user.value = user.value?.copy(time = "${hoursAvailable.value}:${minutesAvailable.value} ${meridiemAvailable.value}")
                    //Register user
                    viewModelScope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main){
                            authStatus.value = AppAuthState.Loading("Loading")
                        }
                        val status = repo.register(user.value!!, user.value?.password!!)
                        withContext(Dispatchers.Main){
                            authStatus.value = status
                        }
                    }

                }
            }
        }

    }

    fun previousPage() {
        page.value = page.value?.minus(1)

    }

    fun setUsername(username: String) {
        user.value = user.value?.copy(username = username)
    }

    fun addGenre(genre: String) {
        user.value = user.value?.copy(genres = user.value?.genres!!.plus(genre))
    }

    fun addPlatform(platform: String) {
        val platforms = user.value?.platforms!!.toMutableList()

        if (platforms.contains(platform)) {
            platforms.remove(platform)
        } else {
            platforms.add(platform)
        }

        user.value = user.value?.copy(platforms = platforms)
    }


    fun getGamesByTitle(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val gamesFetched = gameRepository.getGamesByTitle(title)
            games?.let {
                withContext(Dispatchers.Main) {
                    games.value = gamesFetched
                }
            }

        }
    }

    fun selectDayAvailable(day: String) {
        val days = daysAvailable.value!!.toMutableList()

        if (days.contains(day)) {
            days.remove(day)
        } else {
            days.add(day)
        }

        daysAvailable.value = days


    }

        fun setBiography(biography: String) {
            user.value = user.value?.copy(biography = biography)
        }

        fun addTags(gamertag: String) {
            val gamertags = user.value?.gamertags!!.toMutableList()
            gamertags.add(gamertag)
            user.value = user.value?.copy(gamertags = gamertags)
        }

        fun setGames(games: List<String>) {
            user.value = user.value?.copy(games = games)
        }

        fun setGamertags(gamertags: List<String>) {
            user.value = user.value?.copy(gamertags = gamertags)
        }

        fun setEmail(email: String) {
            user.value = user.value?.copy(email = email)
        }

        fun setPassword(password: String) {
            user.value = user.value?.copy(password = password)
        }

        fun setTime(time: String) {
            user.value = user.value?.copy(time = time)
        }

        fun getAllGames() {
            viewModelScope.launch(Dispatchers.IO) {
                val gamesFetched = gameRepository.getAllGames()
                games.let {
                    withContext(Dispatchers.Main) {
                        games.value = gamesFetched
                    }
                }
            }
        }

        fun addGame(game: Game) {
            val games = user.value?.games!!.toMutableList()
            selectedGames.value = selectedGames.value?.plus(game)
            games.add(game.id)

        }

    fun setHourAvailable(toString: String) {
        hoursAvailable.value = toString
    }

    fun setMinuteAvailable(toString: String) {
        minutesAvailable.value = toString
    }

    fun selectMeridiemAvailable(s: String) {
        meridiemAvailable.value = s

    }

    private fun checkTime(): Boolean {
        return hoursAvailable.value?.isNotEmpty() == true && minutesAvailable.value?.isNotEmpty() == true && meridiemAvailable.value?.isNotEmpty() == true
    }

}
