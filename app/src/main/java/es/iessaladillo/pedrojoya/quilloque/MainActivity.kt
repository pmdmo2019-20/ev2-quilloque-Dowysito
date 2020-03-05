package es.iessaladillo.pedrojoya.quilloque

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(navHostFragment)
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupAppBar()
        setUpNavigationView()
    }

    private fun setUpNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                    R.id.mnuDial -> navController.navigate(R.id.dialFragment)
                    R.id.mnuRecent -> navController.navigate(R.id.recentFragment)
                    R.id.mnuContact -> navController.navigate(R.id.contactsFragment)
                else -> false
            }
            true
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
