package com.kryzl.meetthedevs

import android.os.Bundle
import com.kryzl.meetthedevs.base.presentation.Router
import com.kryzl.meetthedevs.presentation.list.ListFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        AndroidInjection.inject(this)
        setUpRouter()
    }

    private fun setUpRouter() {
        router.attachRouter(this, findViewById(R.id.mainActivity_frameLayout_content))
        router.setRoot(ListFragment())
    }
}
