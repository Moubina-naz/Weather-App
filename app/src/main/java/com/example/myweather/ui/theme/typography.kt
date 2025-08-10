package com.example.myweather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myweather.R

// ----- Font Families -----
val JosefinSans = FontFamily(
    Font(R.font.josefin_sans_regular, FontWeight.Normal),
    Font(R.font.josefin_sans_light, FontWeight.Light),
    Font(R.font.josefin_sans_bold, FontWeight.Bold),
    Font(R.font.josefin_sans_semibold, FontWeight.SemiBold),

)
val portlligatsans = FontFamily(
    Font(R.font.portlligatsans_regular, FontWeight.Normal),

)
val ptmono = FontFamily(
    Font(R.font.ptmono_regular, FontWeight.Light),
)
val AppTypography = Typography(
    displayLarge = TextStyle( // Temperature (uses Josefin Sans Bold)
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Bold,
        fontSize = 64.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle( // City name (Josefin Medium)
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    titleMedium = TextStyle( // Country (Josefin Regular)
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Light,
        fontSize = 23.sp
    ),
    bodyLarge = TextStyle( // Date (PT Mono for techy touch)
        fontFamily = ptmono,
        fontWeight = FontWeight.Light,
        fontSize = 81.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle( // Tabs / "Today" labels
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    headlineMedium = TextStyle( // Weather condition: "Sunny", "Cloudy" (Port Lligat)
        fontFamily = portlligatsans,
        fontWeight = FontWeight.Normal,
        fontSize = 65.sp,
        letterSpacing = 1.sp
    ),
            labelLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
),
    labelSmall = TextStyle(fontWeight = FontWeight.Normal,
        fontSize = 20.sp)

)