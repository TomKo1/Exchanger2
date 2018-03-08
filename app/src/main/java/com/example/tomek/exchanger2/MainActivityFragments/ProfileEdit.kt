package com.example.tomek.exchanger2.MainActivityFragments


import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.example.tomek.exchanger2.FirebaseHelpers.AppUser
import com.example.tomek.exchanger2.FirebaseHelpers.FireBaseHelper
import com.example.tomek.exchanger2.R
import com.example.tomek.exchanger2.generateLocalSensitiveDate
import kotlinx.android.synthetic.main.fragment_profile_edit.*


import java.util.*


/**
 * A [Fragment] subclass for editing profile information.
 * Use the [ProfileEdit.newInstance] factory method to
 * create an instance of this fragment.
 */



class ProfileEdit : Fragment() {


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configEditSaveBtn()

        configBirthTxtViewToast()

        configDescriptionWordCount()

        configBirthBtn()

    }


    private fun configBirthTxtViewToast(){
        edit_profile_birth.setOnClickListener{
            view ->
                    Toast.makeText(context,context.getString(R.string.please_click_btn_to_sel_the_date),Toast.LENGTH_SHORT).show()
        }
    }

    private fun configBirthBtn(){

        edit_profile_birth_button.setOnClickListener{
            view ->
                val calendar:Calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day= calendar.get(Calendar.DAY_OF_MONTH)
                val datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                    datePicker: DatePicker?, y: Int, m: Int, d: Int ->

                        val date:String = String.Companion.generateLocalSensitiveDate(y, m, d)
                        edit_profile_birth.text=date

                },year,month,day)

                datePicker.show()

        }
    }



    private fun configDescriptionWordCount(){
            val maxEditTextDescLength= context.resources.getInteger(R.integer.description_max_length)
            edit_profile_description.addTextChangedListener(object : TextWatcher
            {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var length = edit_profile_description.length()
                    var wordsRemaining = maxEditTextDescLength - length
                    if(wordsRemaining < 11) {
                        edit_profile_description_word_count.setTextColor(Color.RED)
                        }else{
                            edit_profile_description_word_count.setTextColor(Color.BLACK)
                        }
                    edit_profile_description_word_count.text = wordsRemaining.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                    // empty
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // empty
                }

            })
    }



    private fun configEditSaveBtn(){

        edit_save_edit_button.setOnClickListener {
            view ->
                val editName: String = edit_profile_name.text.toString()
                val editCountry: String = edit_profile_country.text.toString()
                val editPhoneNumber: String = edit_profile_phone.text.toString()
                val editDescription: String = edit_profile_description.text.toString()
                val txtViewBirthDay: String = edit_profile_birth.text.toString()



                //TODO make this more user-friendly -> user should be able to update only one info
                if( TextUtils.isEmpty(editName) ||  TextUtils.isEmpty(editCountry) ||
                        TextUtils.isEmpty(editPhoneNumber) || TextUtils.isEmpty(editDescription) ||
                        TextUtils.equals(txtViewBirthDay,context.getString(R.string.please_click_btn_to_sel_the_date))){

                        Toast.makeText(context,context.getString(R.string.edit_fields_not_empty),Toast.LENGTH_SHORT).show()

                }else{

                    val appUser = AppUser(editName, editCountry, editPhoneNumber, editDescription, txtViewBirthDay)

                    val fbHelper = FireBaseHelper(appUser)

                     fbHelper.updateUserInfo(context)


                    //TODO make it more user-friendly - do not delete all
                     clearTextInputs()



                }




        }

    }



    private fun clearTextInputs(){
        edit_profile_name.text=null
        edit_profile_country.text=null
        edit_profile_phone.text=null
        edit_profile_description.text=null
        edit_profile_description.text = null
        edit_profile_birth.text = context.getString(R.string.please_click_btn_to_sel_the_date)
   }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_profile_edit, container, false)
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

        // factory method -> we can add here fragment's param -> easy way
        fun newInstance():ProfileEdit{
            val fragment = ProfileEdit()
            // here we can put args to Bundle and set it for this fragment
            return fragment
        }

    }

}





