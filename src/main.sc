require: functions.js

theme: /

    state: Start
        q!: $regex</start>
        a: Привет. Я могу сообщить погоду в указанном городе. Укажите город.

    state: GetWeather
        intent!: /geo
        script:
            let city = %caila.inflect($parseTree._geo, ["nomn"]);
            openWeatherMapCurrent("metric", "ru", city).then(function (res) {
                if (res && res.weather) {
                    $reactions.answer("В городе " + capitalize(city) + " " + res.weather[0].description + ", " + Math.round(res.main.temp) + "°C градусов");    
                }
                else {
                    $reactions.answer("Проблемы с сервером");    
                }
            }).catch(function (err) {
                $reactions.answer("Проблемы с сервером");
            });
            

    state: Hello
        intent!: /привет
        a: Привет привет

    state: Bye
        intent!: /пока
        a: Пока пока

    state: NoMatch || noContext=true
        event!: noMatch
        a: Я не понял. Напишите название города и я расскажу о погоде.
        go: /GetWeather
