let temperatureData = []; // 전역 변수로 선언

let regID = '11B00000', regIDT = '11B10101', nx = 60, ny = 127;

function selectReg(selectCity) {
    if (selectCity === "") {
        return;
    }
    temperatureData.length = 0;
    $('.weathershortins').html('');
    $('.weatherins').html('');
    switch (selectCity) {
        case 'city1': regID = '11B00000'; regIDT = '11B10101'; nx = 60; ny = 127; break;
        case 'city2': regID = '11H20000'; regIDT = '11H20101'; nx = 98; ny = 76; break;
        case 'city3': regID = '11H10000'; regIDT = '11H10701'; nx = 89; ny = 90; break;
        case 'city4': regID = '11D10000'; regIDT = '11B20201'; nx = 55; ny = 124; break;
        case 'city5': regID = '11F20000'; regIDT = '11F20501'; nx = 58; ny = 74; break;
        case 'city6': regID = '11C20000'; regIDT = '11C20401'; nx = 67; ny = 100; break;
        case 'city7': regID = '11H20000'; regIDT = '11H20101'; nx = 102; ny = 84; break;
        case 'city8': regID = '11C20000'; regIDT = '11C20404'; nx = 66; ny = 103; break;
        case 'city9': regID = '11B00000'; regIDT = '11B20601'; nx = 60; ny = 121; break;
        case 'city10': regID = '11D10000'; regIDT = '11D10301'; nx = 73; ny = 134; break;
        case 'city11': regID = '11C10000'; regIDT = '11C10301'; nx = 69; ny = 107; break;
        case 'city12': regID = '11C10000'; regIDT = '11C20101'; nx = 51; ny = 96; break;
        case 'city13': regID = '11F10000'; regIDT = '11F10201'; nx = 63; ny = 89; break;
        case 'city14': regID = '11F20000'; regIDT = '11F20501'; nx = 50; ny = 67; break;
        case 'city15': regID = '11H10000'; regIDT = '11H10701'; nx = 91; ny = 106; break;
        case 'city16': regID = '11H20000'; regIDT = '11H20301'; nx = 89; ny = 77; break;
        case 'city17': regID = '11G00000'; regIDT = '11G00201'; nx = 52; ny = 38; break;
        default: regID = '11B00000'; regIDT = '11B10101'; nx = 60; ny = 127; break;
    }
    // 기존 날씨 정보를 비웁니다.
    fetchWeatherData();
    fetchMidTemperatureData();
    WeatherData();
}