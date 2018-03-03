package com.example.tomek.exchanger2.MainActivityFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomek.exchanger2.R


/**
 * A [Fragment] subclass for showing full profile.
 * Use the [FullProfileView.newInstance] factory method to
 * create an instance of this fragment.
 */


class FullProfileView : Fragment() {




    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_full_profile_view, container, false)
    }





    //         so far there is no need for fragment - fragment/ fragment - activity communication

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
    */
    /*
    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }
    */


    companion object {

        // factory method -> we can add here fragment's param
        fun newInstance():FullProfileView{
            val fragment = FullProfileView()
            // here we can put args to Bundle and set it for this fragment
            return fragment
        }

    }





}
