
$(document).ready(function() {
    fetchWeatherData();  // 페이지 로드 시 오늘과 내일 날씨 호출
    fetchMidTemperatureData();  // 페이지 로드 시 3~7일 기온 호출
    WeatherData();  // 페이지 로드 시 3~7일 기온 및 날씨 상태 호출
});

// 오늘과 내일의 날씨 데이터를 가져오는 함수
const fetchWeatherData = () => {
    const today = new Date();
    const yesterday = new Date();
    yesterday.setDate(today.getDate() - 1);
    const base_date = today.getHours() < 6 ?
        `${yesterday.getFullYear()}${String(yesterday.getMonth() + 1).padStart(2, '0')}${String(yesterday.getDate()).padStart(2, '0')}` :
        `${today.getFullYear()}${String(today.getMonth() + 1).padStart(2, '0')}${String(today.getDate()).padStart(2, '0')}`;

    const ServiceKey = 'wje5hA%2Bnn3R5RgOMpkRjQoneVn2py4RGqQmFJCCZS2IjYEetSvm7HP%2FngICVZN7%2FB0Mj2ig7Xp2oep7%2B4kcrSQ%3D%3D';
    const pageNo = 1, numOfRows = 750, dataType = 'JSON';

    $.ajax({
        url: `https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${ServiceKey}&pageNo=${pageNo}&numOfRows=750&dataType=${dataType}&base_date=${base_date}&base_time=0500&nx=${nx}&ny=${ny}`,
        type: 'GET',
        success: function(data) {
            let todayWeather = {}, tomorrowWeather = {};
            let todayDateStr = `${today.getFullYear()}${String(today.getMonth() + 1).padStart(2, '0')}${String(today.getDate()).padStart(2, '0')}`;
            let tomorrow = new Date(today.getTime() + (24 * 60 * 60 * 1000));
            let tomorrowDateStr = `${tomorrow.getFullYear()}${String(tomorrow.getMonth() + 1).padStart(2, '0')}${String(tomorrow.getDate()).padStart(2, '0')}`;

            data.response.body.items.item.forEach(item => {
                // 오늘 날짜 데이터 처리
                if (item.fcstDate === todayDateStr) {
                    if (item.category === 'TMP') todayWeather.TMP = item.fcstValue;
                    if (item.category === 'TMX' && item.fcstTime === '1500') todayWeather.TMX = item.fcstValue; // 1500 시간의 TMX만
                    if (item.category === 'TMN' && item.fcstTime === '0600') {
                        // TMN 값이 있을 경우 그대로 사용
                        todayWeather.TMN = item.fcstValue;
                    } else if (item.category === 'TMP') {
                        // TMP 값을 배열에 저장하고, 나중에 최소값을 사용
                        if (!todayWeather.TMPs) todayWeather.TMPs = []; // TMPs 배열이 없으면 초기화
                        todayWeather.TMPs.push(parseFloat(item.fcstValue));
                    }

                    // 이후 모든 데이터를 처리한 후 최저 TMP 값을 TMN으로 설정
                    if (!todayWeather.TMN && todayWeather.TMPs && todayWeather.TMPs.length > 0) {
                        todayWeather.TMN = Math.min(...todayWeather.TMPs); // TMP 값 중 최저값을 TMN으로 사용
                    }

                    if (item.category === 'SKY') setSkyIcon(item.fcstValue, 'today');
                    if (item.category === 'PTY') setPtyIcon(item.fcstValue, 'today');
                }

                // 내일 날짜 데이터 처리
                if (item.fcstDate === tomorrowDateStr) {
                    if (item.category === 'TMP') tomorrowWeather.TMP = item.fcstValue;
                    if (item.category === 'TMX' && item.fcstTime === '1500') tomorrowWeather.TMX = item.fcstValue; // 1500 시간의 TMX만
                    if (item.category === 'TMN' && item.fcstTime === '0600') tomorrowWeather.TMN = item.fcstValue; // 내일 TMN만

                    if (item.category === 'SKY') setSkyIcon(item.fcstValue, 'tomorrow');
                    if (item.category === 'PTY') setPtyIcon(item.fcstValue, 'tomorrow');
                }
            });

            // 결과를 HTML에 반영
            $('#todayTMP').text(todayWeather.TMP || '정보 없음');
            $('#todayTMX').text(todayWeather.TMX || '정보 없음');
            $('#todayTMN').text(todayWeather.TMN || '정보 없음');

            $('#tomorrowTMP').text(tomorrowWeather.TMP || '정보 없음');
            $('#tomorrowTMX').text(tomorrowWeather.TMX || '정보 없음');
            $('#tomorrowTMN').text(tomorrowWeather.TMN || '정보 없음');
        },
        error: function() {
            console.error('날씨 데이터를 가져오는 데 실패했습니다.');
        }
    });
};

// 3~7일의 기온 데이터를 가져오는 함수
const fetchMidTemperatureData = () => {
    const today = new Date();
    const yesterday = new Date();
    yesterday.setDate(today.getDate() - 1);
    const tmFc = today.getHours() < 6 ?
        `${yesterday.getFullYear()}${String(yesterday.getMonth() + 1).padStart(2, '0')}${String(yesterday.getDate()).padStart(2, '0')}1800` :
        `${today.getFullYear()}${String(today.getMonth() + 1).padStart(2, '0')}${String(today.getDate()).padStart(2, '0')}0600`;

    const ServiceKey = 'wje5hA%2Bnn3R5RgOMpkRjQoneVn2py4RGqQmFJCCZS2IjYEetSvm7HP%2FngICVZN7%2FB0Mj2ig7Xp2oep7%2B4kcrSQ%3D%3D';
    const pageNo = 1, numOfRows = 10;

    $.ajax({
        url: `https://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=${ServiceKey}&pageNo=${pageNo}&numOfRows=${numOfRows}&dataType=XML&regId=${regIDT}&tmFc=${tmFc}`,
        type: 'GET',
        success: function(data) {
            $(data).find('item').each(function() {
                for (let i = 3; i <= 7; i++) {
                    const taMin = $(this).find(`taMin${i}`).text();
                    const taMax = $(this).find(`taMax${i}`).text();
                    $(`#day${i}Min`).text(taMin || '정보 없음');
                    $(`#day${i}Max`).text(taMax || '정보 없음');
                }
            });
        },
        error: function() {
            console.error('3~7일 기온 데이터를 가져오는 데 실패했습니다.');
        }
    });
};

// 하늘 상태에 따른 이미지 설정 함수
function setSkyIcon(skyCode, day) {
    let skyDiv = document.getElementById(`skyIcon${day.charAt(0).toUpperCase() + day.slice(1)}`);
    switch (skyCode) {
        case '1': skyDiv.style.backgroundImage = 'url("/weatherimg/SKY1.webp")'; break; // 맑음
        case '3': skyDiv.style.backgroundImage = 'url("/weatherimg/SKY3.webp")'; break; // 구름많음
        case '4': skyDiv.style.backgroundImage = 'url("/weatherimg/SKY4.webp")'; break; // 흐림
        default: skyDiv.style.backgroundImage = 'url("/weatherimg/esteregg.webp")'; break;  // 이미지 없음
    }
    skyDiv.style.backgroundSize = "cover";
    skyDiv.style.backgroundSize = "100% 100%";
    skyDiv.style.boxSizing = "border-box";
    skyDiv.style.overflow = "hidden";
    skyDiv.style.zIndex = "1";
}

// 강수 상태에 따른 이미지 설정 함수
function setPtyIcon(ptyCode, day) {
    let ptyImg = document.getElementById(`ptyIcon${day.charAt(0).toUpperCase() + day.slice(1)}`);

    switch (ptyCode) {
        case '0': ptyImg.src = '/weatherimg/PTY0.png'; break; // 강수 없음
        case '1': ptyImg.src = '/weatherimg/PTY1.png'; break; // 비
        case '2': ptyImg.src = '/weatherimg/PTY2.png'; break; // 비/눈
        case '3': ptyImg.src = '/weatherimg/PTY3.png'; break; // 눈
        case '4': ptyImg.src = '/weatherimg/PTY4.png'; break; // 소나기
        default: ptyImg.src = 'url("/weatherimg/esteregg.webp")'; break;  // 이미지 없음
    }
}


const WeatherDataIcon = () => {

    const today = new Date(), yesterday = new Date();
    yesterday.setDate(today.getDate() - 1);

    let tmFc = today.getHours() > 6 ?
        `${today.getFullYear()}${String(today.getMonth() + 1).padStart(2, '0')}${String(today.getDate()).padStart(2, '0')}0600` :
        `${yesterday.getFullYear()}${String(yesterday.getMonth() + 1).padStart(2, '0')}${String(yesterday.getDate()).padStart(2, '0')}1800`;

    const ServiceKey = 'wje5hA%2Bnn3R5RgOMpkRjQoneVn2py4RGqQmFJCCZS2IjYEetSvm7HP%2FngICVZN7%2FB0Mj2ig7Xp2oep7%2B4kcrSQ%3D%3D';
    const pageNo = 1, numOfRows = 10, dataType = 'XML', temperatureData = [
        { dayIndex: 3, amId: 'day3Am', pmId: 'day3Pm' },
        { dayIndex: 4, amId: 'day4Am', pmId: 'day4Pm' },
        { dayIndex: 5, amId: 'day5Am', pmId: 'day5Pm' },
        { dayIndex: 6, amId: 'day6Am', pmId: 'day6Pm' },
        { dayIndex: 7, amId: 'day7Am', pmId: 'day7Pm' }
    ];


    // 최저기온 데이터 안남어오면 그날 기온중 제일 낮은걸로 대체용.
    $.ajax({
        url: `https://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=${ServiceKey}&pageNo=${pageNo}&numOfRows=${numOfRows}&dataType=${dataType}&regId=${regID}&tmFc=${tmFc}`,
        type: 'GET',
        success: function(data) {
            $(data).find('items item').each(function() {
                temperatureData.forEach(item => {
                    const wfam = $(this).find(`wf${item.dayIndex}Am`).text();
                    const wfpm = $(this).find(`wf${item.dayIndex}Pm`).text();

                    // 날씨 아이콘 설정
                    setWeatherIcon({ am: wfam, pm: wfpm }, item.dayIndex);

                    // HTML에 값 삽입
                    $(`#${item.amId}`).text(wfam || 'No Data');
                    $(`#${item.pmId}`).text(wfpm || 'No Data');
                });
            });
        },
        error: function(xhr, status, error) {
            console.error('기온 데이터를 가져오는 데 실패했습니다.', error, status, xhr);
        }
    });
};

// 3~7일 날씨 상태에 따른 이미지 설정 함수
function setWeatherIcon(wfCode, day) {
    let dayAmElement = document.getElementById(`day${day}Am`);
    let dayPmElement = document.getElementById(`day${day}Pm`);

    const ptyCodeToImage = {
        '맑음': '/weatherimg/PTY0.png',
        '구름많음': '/weatherimg/PTY3.png',
        '흐림': '/weatherimg/PTY3.png',
        '비': '/weatherimg/PTY1.png',
        '눈': '/weatherimg/PTY4.png',
        '비/눈': '/weatherimg/PTY2.png',
        '소나기': '/weatherimg/PTY1.png',
        '구름많고 비': '/weatherimg/PTY1.png',
        '구름많고 비/눈': '/weatherimg/PTY2.png',
        '구름많고 소나기': '/weatherimg/PTY1.png',
        '흐리고 비': '/weatherimg/PTY1.png',
        '흐리고 눈': '/weatherimg/PTY4.png',
        '흐리고 비/눈': '/weatherimg/PTY2.png',
        '흐리고 소나기': '/weatherimg/PTY1.png',
    };

    // 오전 및 오후 상태 이미지 경로 설정을 통합
    const imageSrc = ptyCodeToImage[wfCode.am] || ptyCodeToImage[wfCode.pm] || '/weatherimg/esteregg.webp';

    // 오전 이미지 삽입
    if (dayAmElement) {
        dayAmElement.src = imageSrc;
        dayAmElement.alt = wfCode.am;
    }

    // 오후 이미지 삽입
    if (dayPmElement) {
        dayPmElement.src = imageSrc;
        dayPmElement.alt = wfCode.pm;
    }
}

// 날씨 데이터를 가져와서 각 날씨 카드에 반영하는 함수
function WeatherData() {
    const today = new Date();
    const yesterday = new Date(today);
    yesterday.setDate(today.getDate() - 1);

    let tmFc = today.getHours() >= 6 ?
        `${today.getFullYear()}${String(today.getMonth() + 1).padStart(2, '0')}${String(today.getDate()).padStart(2, '0')}0600` :
        `${yesterday.getFullYear()}${String(yesterday.getMonth() + 1).padStart(2, '0')}${String(yesterday.getDate()).padStart(2, '0')}1800`;

    const ServiceKey = 'wje5hA%2Bnn3R5RgOMpkRjQoneVn2py4RGqQmFJCCZS2IjYEetSvm7HP%2FngICVZN7%2FB0Mj2ig7Xp2oep7%2B4kcrSQ%3D%3D'; // Replace with your actual service key
    const pageNo = 1, numOfRows = 10;

    $.ajax({
        url: `https://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=${ServiceKey}&pageNo=${pageNo}&numOfRows=${numOfRows}&dataType=XML&regId=${regID}&tmFc=${tmFc}`,
        type: 'GET',
        success: function (data) {
            $(data).find('item').each(function () {
                for (let i = 3; i <= 7; i++) {
                    const wfAm = $(this).find(`wf${i}Am`).text();
                    const wfPm = $(this).find(`wf${i}Pm`).text();

                    // 날씨 아이콘 설정
                    setWeatherIcon({ am: wfAm, pm: wfPm }, i);
                }
            });
        },
        error: function (xhr, status, error) {
            console.error('3~7일 기온 데이터를 가져오는 데 실패했습니다.', error, status, xhr);
        }
    });
}

// 페이지 로드 시 실행
$(document).ready(function() {
    WeatherDataIcon();
});
