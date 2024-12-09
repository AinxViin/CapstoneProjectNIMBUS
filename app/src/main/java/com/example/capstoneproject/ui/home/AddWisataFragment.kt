package com.example.capstoneproject.ui.addwisata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstoneproject.R
import com.example.capstoneproject.response.WisataResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddWisataFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddWisataFragment"
        private const val ARG_WISATA = "arg_wisata"

        fun newInstance(wisata: WisataResponse): AddWisataFragment {
            val fragment = AddWisataFragment()
            val bundle = Bundle().apply {
                putParcelable(ARG_WISATA, wisata)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private var wisata: WisataResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wisata = it.getParcelable(ARG_WISATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_wisata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Tambahkan logika untuk menangani wisata yang dipilih
    }
}
