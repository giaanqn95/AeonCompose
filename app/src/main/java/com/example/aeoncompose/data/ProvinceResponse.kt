package com.example.aeoncompose.data

import android.content.Context

class ProvinceResponse(val district: ArrayList<District>): BaseAddress() {
    override fun getName(context: Context): String {
        return ""
    }
}

class District(val ward: ArrayList<Ward>, val parent: String): BaseAddress() {
    val DISTRICT_TYPE_TINH = "T"
    val DISTRICT_TYPE_THANH_PHO = "TP"
    val DISTRICT_TYPE_THI_XA = "TX"
    val DISTRICT_TYPE_QUAN = "Q"
    val DISTRICT_TYPE_DAO = "D"
    val DISTRICT_TYPE_QUAN_DAO = "QĐ"
    val DISTRICT_TYPE_PHUONG = "P"
    val DISTRICT_TYPE_THI_TRAN = "TT"
    val DISTRICT_TYPE_XA = "X"
    val DISTRICT_TYPE_HUYEN = "H"
    val REGEX_NUMBER = "\"^[0-9]*$\""
    override fun getName(context: Context): String {
        return ""
    }


}


class Ward(val parent: String) : BaseAddress() {

    override fun getName(context: Context): String {
        return ""
    }
}

abstract class BaseAddress(val name: String = "", val type: String = "", val code: String = "") {
    abstract fun getName(context: Context): String

}
