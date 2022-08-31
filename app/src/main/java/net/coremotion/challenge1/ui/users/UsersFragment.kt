package net.coremotion.challenge1.ui.users

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.coremotion.challenge1.databinding.UsersFragmentBinding
import net.coremotion.challenge1.ui.base.BaseFragment
import net.coremotion.challenge1.ui.users.source.UsersAdapter

@AndroidEntryPoint
class UsersFragment : BaseFragment<UsersFragmentBinding>(UsersFragmentBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private val usersAdapter by lazy { UsersAdapter() }

    override fun start() {
        initUsersRecyclerView()
        setObservers()
        setListeners()
    }

    private fun setListeners() {

    }

    private fun initUsersRecyclerView() {
        usersAdapter.userItemOnClick = {
            if (it != null) {
                openUserDetail(it)
            }

        }
        binding.usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
    }

    private fun openUserDetail(userId: Int) {
        findNavController().navigate(
            UsersFragmentDirections.actionNewsFragmentToUserDetailFragment(
                userId
            )
        )
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usersFlow().collect { pagingData ->
                usersAdapter.submitData(pagingData)
            }
        }
    }
}