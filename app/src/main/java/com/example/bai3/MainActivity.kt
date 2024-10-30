package com.example.bai3

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerDistrict: Spinner
    private lateinit var spinnerWard: Spinner
    private lateinit var addressHelper: AddressHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các Spinner
        spinnerProvince = findViewById(R.id.spinnerProvince)
        spinnerDistrict = findViewById(R.id.spinnerDistrict)
        spinnerWard = findViewById(R.id.spinnerWard)

        // Khởi tạo AddressHelper
        addressHelper = AddressHelper(resources)

        // Thiết lập dữ liệu cho Spinner tỉnh/thành phố
        val provinces = addressHelper.getProvinces()
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProvince.adapter = provinceAdapter

        // Xử lý khi chọn tỉnh/thành phố
        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                loadDistricts(selectedProvince)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    // Hàm tải quận/huyện theo tỉnh đã chọn
    private fun loadDistricts(province: String) {
        val districts = addressHelper.getDistricts(province)
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDistrict.adapter = districtAdapter

        spinnerDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                val selectedDistrict = districts[position]
                loadWards(province, selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    // Hàm tải xã/phường theo quận/huyện đã chọn
    private fun loadWards(province: String, district: String) {
        val wards = addressHelper.getWards(province, district)
        val wardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wards)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerWard.adapter = wardAdapter
    }
}

