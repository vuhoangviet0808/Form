package com.example.bai3

import android.content.res.Resources
import org.json.JSONObject
import java.io.InputStreamReader

class AddressHelper(resources: Resources) {

    private val data: JSONObject

    init {
        val inputStream = resources.openRawResource(R.raw.tree)
        val reader = InputStreamReader(inputStream)
        val content = reader.readText()
        reader.close()
        data = JSONObject(content)
    }

    fun getProvinces(): List<String> {
        val list = mutableListOf<String>()
        val keys = data.keys()
        for (key in keys) {
            list.add(data.getJSONObject(key).getString("name"))
        }
        return list
    }

    fun getDistricts(province: String): List<String> {
        val list = mutableListOf<String>()
        for (key in data.keys()) {
            val provinceObj = data.getJSONObject(key)
            if (provinceObj.getString("name") == province) {
                val districts = provinceObj.getJSONObject("quan-huyen")
                for (districtKey in districts.keys()) {
                    list.add(districts.getJSONObject(districtKey).getString("name"))
                }
                break
            }
        }
        return list
    }

    fun getWards(province: String, district: String): List<String> {
        val list = mutableListOf<String>()
        for (key in data.keys()) {
            val provinceObj = data.getJSONObject(key)
            if (provinceObj.getString("name") == province) {
                val districts = provinceObj.getJSONObject("quan-huyen")
                for (districtKey in districts.keys()) {
                    val districtObj = districts.getJSONObject(districtKey)
                    if (districtObj.getString("name") == district) {
                        val wards = districtObj.getJSONObject("xa-phuong")
                        for (wardKey in wards.keys()) {
                            list.add(wards.getJSONObject(wardKey).getString("name"))
                        }
                        break
                    }
                }
            }
        }
        return list
    }
}
