package com.test.data.mappers

import com.test.data.R

sealed class WeatherCondition(val code: Int, val descriptionResId: Int) {
    object ClearSky : WeatherCondition(0, R.string.weather_clear_sky)
    object MainlyClear : WeatherCondition(1, R.string.weather_mainly_clear)
    object PartlyCloudy : WeatherCondition(2, R.string.weather_partly_cloudy)
    object Overcast : WeatherCondition(3, R.string.weather_overcast)
    object Fog : WeatherCondition(45, R.string.weather_fog)
    object RimeFog : WeatherCondition(48, R.string.weather_rime_fog)
    object LightDrizzle : WeatherCondition(51, R.string.weather_light_drizzle)
    object ModerateDrizzle : WeatherCondition(53, R.string.weather_moderate_drizzle)
    object DenseDrizzle : WeatherCondition(55, R.string.weather_dense_drizzle)
    object LightFreezingDrizzle : WeatherCondition(56, R.string.weather_light_freezing_drizzle)
    object DenseFreezingDrizzle : WeatherCondition(57, R.string.weather_dense_freezing_drizzle)
    object LightRain : WeatherCondition(61, R.string.weather_light_rain)
    object ModerateRain : WeatherCondition(63, R.string.weather_moderate_rain)
    object HeavyRain : WeatherCondition(65, R.string.weather_heavy_rain)
    object LightFreezingRain : WeatherCondition(66, R.string.light_freezing_rain)
    object HeavyFreezingRain : WeatherCondition(67, R.string.heavy_freezing_rain)
    object LightSnowFall : WeatherCondition(71, R.string.weather_light_snow_fall)
    object ModerateSnowFall : WeatherCondition(73, R.string.weather_moderate_snow_fall)
    object HeavySnowFall : WeatherCondition(75, R.string.weather_heavy_snow_fall)
    object SnowGrains : WeatherCondition(77, R.string.weather_snow_grains)
    object LightRainShowers : WeatherCondition(80, R.string.weather_light_rain_showers)
    object ModerateRainShowers : WeatherCondition(81, R.string.weather_moderate_rain_showers)
    object ViolentRainShowers : WeatherCondition(82, R.string.weather_violent_rain_showers)
    object LightSnowShowers : WeatherCondition(85, R.string.weather_light_snow_showers)
    object HeavySnowShowers : WeatherCondition(86, R.string.weather_heavy_snow_showers)
    object Thunderstorm : WeatherCondition(95, R.string.weather_thunderstorm)
    object ThunderstormWithLightHail :
        WeatherCondition(96, R.string.weather_thunderstorm_light_hail)

    object ThunderstormWithHeavyHail :
        WeatherCondition(99, R.string.weather_thunderstorm_heavy_hail)

    object Unknown : WeatherCondition(-1, R.string.weather_unknown_condition)


    companion object {
        fun fromCode(code: Int): WeatherCondition {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Fog
                48 -> RimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> LightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingRain
                67 -> HeavyFreezingRain
                71 -> LightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> LightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> LightSnowShowers
                86 -> HeavySnowShowers
                95 -> Thunderstorm
                96 -> ThunderstormWithLightHail
                99 -> ThunderstormWithHeavyHail
                else -> Unknown
            }
        }
    }
}
