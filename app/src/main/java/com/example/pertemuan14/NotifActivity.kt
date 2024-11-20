package com.example.pertemuan14

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pertemuan14.databinding.ActivityNotifBinding

class NotifActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifBinding
    private val channelId = "TEST_NOTIF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotifBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.btnNotif.setOnClickListener {

            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            }
            else {
                0
            }

            val intent = Intent(this@NotifActivity, NotifReceiver::class.java). putExtra("MESSAGE", "Baca selengekapnya....")

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                flag
            )

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Notifku")
                .setContentText("Hello World!") //isi pesan bebas
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(0, "Baca notif", pendingIntent)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notifChannel = NotificationChannel(
                    channelId, // Id channel
                    "Notifku", // Nama channel notifikasi
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                with(notifManager) {
                    createNotificationChannel(notifChannel)
//                    notify(0, builder.build())
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notifManager.notify(0, builder.build())
                } else {
                    requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
                }
            } else {
                notifManager.notify(0, builder.build())
            }

        }
    }
}