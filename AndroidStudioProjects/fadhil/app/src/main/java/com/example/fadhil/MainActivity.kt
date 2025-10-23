package com.example.fadhil

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var shapeSpinner: Spinner
    private lateinit var inputContainer: LinearLayout
    private lateinit var calculateButton: Button
    private lateinit var resultContainer: LinearLayout
    private lateinit var areaResult: TextView
    private lateinit var perimeterResult: TextView
    private lateinit var btnTentang: Button

    private val shapes = arrayOf(
        "Pilih Bangun Datar...",
        "Persegi", "Persegi Panjang", "Segitiga",
        "Lingkaran", "Jajar Genjang", "Belah Ketupat",
        "Layang-layang", "Trapesium"
    )

    private val df = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shapeSpinner = findViewById(R.id.shapeSpinner)
        inputContainer = findViewById(R.id.inputContainer)
        calculateButton = findViewById(R.id.calculateButton)
        resultContainer = findViewById(R.id.resultContainer)
        areaResult = findViewById(R.id.areaResult)
        perimeterResult = findViewById(R.id.perimeterResult)
        btnTentang = findViewById(R.id.btnTentang)

        setupSpinner()
        calculateButton.setOnClickListener { calculateResults() }

        // Tombol menuju halaman TentangActivity
        btnTentang.setOnClickListener {
            val intent = Intent(this, TentangActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shapes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shapeSpinner.adapter = adapter

        shapeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                createInputFields(position)
                resultContainer.visibility = View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun createInputFields(shapeIndex: Int) {
        inputContainer.removeAllViews()
        if (shapeIndex == 0) {
            inputContainer.visibility = View.GONE
            return
        }
        inputContainer.visibility = View.VISIBLE

        val title = TextView(this)
        title.text = "Masukkan Nilai:"
        title.textSize = 18f
        inputContainer.addView(title)

        when (shapeIndex) {
            1 -> addInputField("Sisi", "sisi")
            2 -> {
                addInputField("Panjang", "panjang")
                addInputField("Lebar", "lebar")
            }
            3 -> {
                addInputField("Alas", "alas")
                addInputField("Tinggi", "tinggi")
                addInputField("Sisi A", "sisiA")
                addInputField("Sisi B", "sisiB")
                addInputField("Sisi C", "sisiC")
            }
            4 -> addInputField("Jari-jari", "jariJari")
            5 -> {
                addInputField("Alas", "alas")
                addInputField("Tinggi", "tinggi")
                addInputField("Sisi Miring A", "sisiA")
                addInputField("Sisi Miring B", "sisiB")
            }
            6 -> {
                addInputField("Diagonal 1", "d1")
                addInputField("Diagonal 2", "d2")
                addInputField("Sisi", "sisi")
            }
            7 -> {
                addInputField("Diagonal 1", "d1")
                addInputField("Diagonal 2", "d2")
                addInputField("Sisi A", "sisiA")
                addInputField("Sisi B", "sisiB")
            }
            8 -> {
                addInputField("Sisi Atas", "atas")
                addInputField("Sisi Bawah", "bawah")
                addInputField("Tinggi", "tinggi")
                addInputField("Sisi Miring A", "sisiA")
                addInputField("Sisi Miring B", "sisiB")
            }
        }
    }

    private fun addInputField(label: String, tag: String) {
        val labelView = TextView(this)
        labelView.text = "$label:"
        inputContainer.addView(labelView)

        val input = EditText(this)
        input.hint = "Masukkan $label"
        input.tag = tag
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        inputContainer.addView(input)
    }

    private fun getInputValue(tag: String): Double {
        val input = inputContainer.findViewWithTag<EditText>(tag)
        val text = input?.text.toString()
        return if (text.isNotEmpty()) text.toDouble() else 0.0
    }

    private fun calculateResults() {
        val selectedShape = shapeSpinner.selectedItemPosition
        if (selectedShape == 0) {
            Toast.makeText(this, "Pilih bangun datar dulu!", Toast.LENGTH_SHORT).show()
            return
        }

        var area = 0.0
        var perimeter = 0.0

        when (selectedShape) {
            1 -> { // Persegi
                val sisi = getInputValue("sisi")
                area = sisi * sisi
                perimeter = 4 * sisi
            }
            2 -> { // Persegi Panjang
                val panjang = getInputValue("panjang")
                val lebar = getInputValue("lebar")
                area = panjang * lebar
                perimeter = 2 * (panjang + lebar)
            }
            3 -> { // Segitiga
                val alas = getInputValue("alas")
                val tinggi = getInputValue("tinggi")
                val a = getInputValue("sisiA")
                val b = getInputValue("sisiB")
                val c = getInputValue("sisiC")
                area = 0.5 * alas * tinggi
                perimeter = a + b + c
            }
            4 -> { // Lingkaran
                val r = getInputValue("jariJari")
                area = Math.PI * r * r
                perimeter = 2 * Math.PI * r
            }
            5 -> { // Jajar Genjang
                val alas = getInputValue("alas")
                val tinggi = getInputValue("tinggi")
                val a = getInputValue("sisiA")
                val b = getInputValue("sisiB")
                area = alas * tinggi
                perimeter = 2 * (a + b)
            }
            6 -> { // Belah Ketupat
                val d1 = getInputValue("d1")
                val d2 = getInputValue("d2")
                val sisi = getInputValue("sisi")
                area = 0.5 * d1 * d2
                perimeter = 4 * sisi
            }
            7 -> { // Layang-layang
                val d1 = getInputValue("d1")
                val d2 = getInputValue("d2")
                val a = getInputValue("sisiA")
                val b = getInputValue("sisiB")
                area = 0.5 * d1 * d2
                perimeter = 2 * (a + b)
            }
            8 -> { // Trapesium
                val atas = getInputValue("atas")
                val bawah = getInputValue("bawah")
                val tinggi = getInputValue("tinggi")
                val a = getInputValue("sisiA")
                val b = getInputValue("sisiB")
                area = 0.5 * (atas + bawah) * tinggi
                perimeter = atas + bawah + a + b
            }
        }

        areaResult.text = "Luas: ${df.format(area)}"
        perimeterResult.text = "Keliling: ${df.format(perimeter)}"
        resultContainer.visibility = View.VISIBLE
    }
}