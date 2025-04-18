package jp.co.zaico.codingtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import jp.co.zaico.codingtest.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return _binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _viewModel = ThirdViewModel(requireContext())

        _binding!!.buttonSubmit.setOnClickListener { it
            val title = _binding!!.editTitle.text.toString()
            val category = _binding!!.editCategory.text.toString()
            val state = _binding!!.editState.text.toString()

            val success = _viewModel.addInventory(title, category, state)

            if (success) {
                Toast.makeText(requireContext(), "登録成功！", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "登録失敗", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}