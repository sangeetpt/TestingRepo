package com.infosys.kbcmovies.uicomponents

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.infosys.kbcmovies.BaseActivity
import com.infosys.kbcmovies.R
import com.infosys.kbcmovies.adapter.MoviesAdapter
import com.infosys.kbcmovies.model.MovieModel
import com.infosys.kbcmovies.repo.ApiInterface
import com.infosys.kbcmovies.uicomponents.ui.login.biometric.BiometricLogActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private lateinit var moviesAdapter: MoviesAdapter

class MovieListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        callSearchFn()
        retrofitApiCall()
        callLogout()
    }

    private fun callSearchFn(){
        val searchIcon = titleSearch.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.GRAY)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        titleSearch.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        titleSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                moviesAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun retrofitApiCall() {

        val apiCall = ApiInterface.create()

        apiCall.getMovies().enqueue(object : Callback<ArrayList<MovieModel>> {
            override fun onResponse(
                call: Call<ArrayList<MovieModel>>,
                response: Response<ArrayList<MovieModel>>
            ) {
                println("ApiCall Success")
                if (response.code() == 200) {
                    val movieList = response.body()!!
                    moviesAdapter = MoviesAdapter(this@MovieListActivity, movieList)
                    moviesRecyclerView.adapter = moviesAdapter
                    moviesRecyclerView.apply {
                        layoutManager = LinearLayoutManager(applicationContext)
                        adapter = moviesAdapter
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<MovieModel>>, t: Throwable) {
                println("ApiCall Failed")
            }
        })
    }

    private fun callLogout() {
        btnLogout.setOnClickListener {
            finish()
            val intent = Intent(this, BiometricLogActivity::class.java)
            startActivity(intent)
        }
    }


}