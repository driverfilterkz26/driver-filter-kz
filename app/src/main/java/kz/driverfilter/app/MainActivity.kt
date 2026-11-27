package kz.driverfilter.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    private fun makeInput(hint: String): EditText {
        return EditText(this).apply {
            this.hint = hint
            textSize = 18f
            inputType = 8194
            setPadding(24, 12, 24, 12)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 30, 24, 24)
            setBackgroundColor(Color.WHITE)
        }

        val title = TextView(this).apply {
            text = "DRIVER FILTER KZ"
            textSize = 26f
            setTextColor(Color.rgb(20, 90, 160))
            gravity = Gravity.CENTER
        }
        layout.addView(title)

        val subtitle = TextView(this).apply {
            text = "Помощник водителя Яндекс Про"
            textSize = 16f
            gravity = Gravity.CENTER
        }
        layout.addView(subtitle)

        val fare = makeInput("Цена заказа (₸)")
        val pickup = makeInput("Подача до клиента (км)")
        val trip = makeInput("Расстояние поездки (км)")

        layout.addView(fare)
        layout.addView(pickup)
        layout.addView(trip)

        val button = Button(this).apply {
            text = "ПРОВЕРИТЬ ЗАКАЗ"
        }
        layout.addView(button)

        val result = TextView(this).apply {
            text = "Введите данные заказа"
            textSize = 19f
            setPadding(0, 30, 0, 20)
            setTextColor(Color.BLACK)
        }
        layout.addView(result)

        button.setOnClickListener {
            val price = fare.text.toString().toDoubleOrNull()
            val p = pickup.text.toString().toDoubleOrNull()
            val t = trip.text.toString().toDoubleOrNull()

            if (price == null || p == null || t == null ||
                price <= 0 || p < 0 || t <= 0) {
                result.text = "Проверьте введённые данные"
            } else {
                val distance = p + t
                val rate = price / distance

                val decision = when {
                    p > 5 -> "ДАЛЕКАЯ ПОДАЧА!"
                    rate < 300 -> "НИЗКИЙ ДОХОД"
                    else -> "ЗАКАЗ ПОДХОДИТ ПО РАСЧЁТУ"
                }

                result.text = """
                    $decision

                    Цена: ${price.toInt()} ₸
                    Подача: $p км
                    Поездка: $t км
                    Всего: %.1f км

                    Доход: %.0f ₸/км
                """.trimIndent().format(distance, rate)
            }
        }

        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }
}
