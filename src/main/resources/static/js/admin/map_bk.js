// Drawing Manager로 도형을 그릴 지도 div
var drawingMapContainer = document.getElementById('drawingMap'),
    drawingMap = {
        center: new kakao.maps.LatLng(35.02035492064902, 126.79383256393594), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var drawingMap = new kakao.maps.Map(drawingMapContainer, drawingMap)
    , overlays = [] // 지도에 그려진 도형을 담을 배열
    , customOverlay = new kakao.maps.CustomOverlay({})
    , infowindow = new kakao.maps.InfoWindow({removable: true});

var options = { // Drawing Manager를 생성할 때 사용할 옵션입니다
    map: drawingMap, // Drawing Manager로 그리기 요소를 그릴 map 객체입니다
    drawingMode: [ // Drawing Manager로 제공할 그리기 요소 모드입니다
        kakao.maps.drawing.OverlayType.MARKER,
        kakao.maps.drawing.OverlayType.POLYGON
    ],
    // 사용자에게 제공할 그리기 가이드 툴팁입니다
    // 사용자에게 도형을 그릴때, 드래그할때, 수정할때 가이드 툴팁을 표시하도록 설정합니다
    guideTooltip: ['draw', 'drag', 'edit'],
    markerOptions: { // 마커 옵션입니다
        draggable: true, // 마커를 그리고 나서 드래그 가능하게 합니다
        removable: true // 마커를 삭제 할 수 있도록 x 버튼이 표시됩니다
    },
    polygonOptions: {
        draggable: true,
        removable: true,
        editable: true,
        strokeColor: '#330000',
        fillColor: '#FF3333',
        fillOpacity: 0.5,
        hintStrokeStyle: 'dash',
        hintStrokeOpacity: 0.5
    }
};

// 다각형에 마우스오버 이벤트가 발생했을 때 변경할 채우기 옵션입니다
var mouseoverOption = {
    fillColor: '#EFFFED', // 채우기 색깔입니다
    fillOpacity: 0.8 // 채우기 불투명도 입니다
};

// 다각형에 마우스아웃 이벤트가 발생했을 때 변경할 채우기 옵션입니다
var mouseoutOption = {
    fillColor: '#FF3333', // 채우기 색깔입니다
    fillOpacity: 0.7 // 채우기 불투명도 입니다
};

// 포함여부
let checking = false;
// 폴리건 좌표 추가 new Point(x, y)
let polygonPoints = [];

// 위에 작성한 옵션으로 Drawing Manager를 생성합니다
var manager = new kakao.maps.drawing.DrawingManager(options);

// 버튼 클릭 시 호출되는 핸들러 입니다
function selectOverlay(type) {
    // 그리기 중이면 그리기를 취소합니다
    manager.cancel();

    // 클릭한 그리기 요소 타입을 선택합니다
    manager.select(kakao.maps.drawing.OverlayType[type]);
}

// Drawing Manager에서 데이터를 가져와 도형을 표시할 아래쪽 지도 div
/*
var mapContainer = document.getElementById('map'),
    mapOptions = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };
*/

// 폴리곤 생성 후 새로 그릴 때 생성된 폴리곤 삭제를 위해 manager 데이터 저장
let drawingDataTargets = [];
manager.addListener('drawend', function(data) {
    drawingDataTargets.push(data.target);
});

// 가져오기 버튼을 클릭하면 호출되는 핸들러 함수입니다
// Drawing Manager로 그려진 객체 데이터를 가져와 아래 지도에 표시합니다
function getDataFromDrawingMap() {

    // Drawing Manager에서 그려진 데이터 정보를 가져옵니다
    var data = manager.getData();

    drawMarker(data[kakao.maps.drawing.OverlayType.MARKER]);
    let polygonData = data[kakao.maps.drawing.OverlayType.POLYGON];

    let errorMsg = '실패';
    let callBackFn = function (data) {
        if (polygonData.length === 0) {
            alert('구역을 설정하시기 바랍니다.');
            return false;
        } else {
            // 아래 지도에 그려진 도형이 있다면 모두 지웁니다
            removeOverlays();
            // 지도에 가져온 데이터로 도형들을 그립니다
            let areas = getPolygonData();
            areas.forEach(function (element) {
                polygonData.push(element);
            })
            drawingPolygon(polygonData);
            // 생성한 폴리곤 삭제
            removeDrawingOverlays();
        }
    }
    commonAjax("/admin/map/polygonInsert", callBackFn, 'POST', JSON.stringify(polygonData), errorMsg);

}

// 생성한 그리기 도형 삭제
function removeDrawingOverlays() {
    drawingDataTargets.forEach(function (element) {
        manager.remove(element);
    })
}


// 아래 지도에 그려진 도형이 있다면 모두 지웁니다
function removeOverlays() {
    var len = overlays.length, i = 0;

    for (; i < len; i++) {
        overlays[i].setMap(null);
    }

    overlays = [];
}

// Drawing Manager에서 가져온 데이터 중 마커를 아래 지도에 표시하는 함수입니다
function drawMarker(markers) {
    var len = markers.length, i = 0;

    for (; i < len; i++) {
        var marker = new kakao.maps.Marker({
            map: drawingMap,
            position: new kakao.maps.LatLng(markers[i].y, markers[i].x),
            zIndex: markers[i].zIndex
        });

        overlays.push(marker);
    }
}

function drawingPolygon(polygons) {
    var len = polygons.length, i = 0;
    removeOverlays();

    // 지도에 영역데이터를 폴리곤으로 표시합니다
    for (; i< len; i++) {
        displayArea(polygons[i]);
    }
}

// 다각형을 생상하고 이벤트를 등록하는 함수입니다
function displayArea(area) {

    var path = pointsToPath(area.points);
    var style = area.options;
    // 다각형을 생성합니다
    var polygon = new kakao.maps.Polygon({
        map: drawingMap, // 다각형을 표시할 지도 객체
        path: path,
        strokeColor: style.strokeColor,
        strokeOpacity: style.strokeOpacity,
        strokeStyle: style.strokeStyle,
        strokeWeight: style.strokeWeight,
        fillColor: style.fillColor,
        fillOpacity: style.fillOpacity
    });

    kakao.maps.event.addListener(polygon, 'click', function () {
        checking = true
    });

    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
        polygon.setOptions(mouseoverOption);
    });

    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다
    kakao.maps.event.addListener(polygon, 'mouseout', function() {
        polygon.setOptions(mouseoutOption);
    });

    // 다각형에 마우스다운 이벤트를 등록합니다
    var downCount = 0;
    kakao.maps.event.addListener(polygon, 'mousedown', function(mouseEvent) {
        console.log(event);
        var resultDiv = document.getElementById('result');
        resultDiv.innerHTML = '다각형에 mousedown 이벤트가 발생했습니다!' + (++downCount);
        var content = '<div class="info">' +
            '   <div class="title">' + area.seq + '</div>' +
            '</div>';
        infowindow.setContent(content);
        infowindow.setPosition(mouseEvent.latLng);
        infowindow.setMap(drawingMap);
    });
    overlays.push(polygon);
}

/**************************************
 * ****** 폴리곤 내부 포함여부 확인 ******
 **************************************/
kakao.maps.event.addListener(drawingMap, 'click', function(mouseEvent) {
    var latlng = mouseEvent.latLng;
    console.log('click! ' + latlng.toString());
    console.log(polygonPoints)
    console.log("x : " + latlng.getLat() + ", y : " + latlng.getLng());
    let p = new Point(latlng.getLng(), latlng.getLat());

    var len = polygonPoints.length;
    for (let i = 0; i < len; i++) {
        let onePolygon = polygonPoints[i];
        let n = onePolygon.length;

        if (isInside(onePolygon, n, p)) {
            console.log(i + " : Yes");
        } else {
            //console.log(i + " : No");
        }
    }
});


// Drawing Manager에서 가져온 데이터 중
// 선과 다각형의 꼭지점 정보를 kakao.maps.LatLng객체로 생성하고 배열로 반환하는 함수입니다
function pointsToPath(points) {
    var len = points.length,
        path = [],
        i = 0;

    for (; i < len; i++) {
        var latlng = new kakao.maps.LatLng(points[i].y, points[i].x);
        path.push(latlng);
    }

    return path;
}

let polygonStyle = {
    "draggable": true,
    "removable": true,
    "editable": true,
    "strokeColor": "#330000",
    "strokeWeight": 2,
    "strokeStyle": "solid",
    "strokeOpacity": 1,
    "fillColor": "#FF3333",
    "fillOpacity": 0.5
};

function getPolygonData() {
    let areas = []
    for (let j = 0; j < zonePolygon.length; j++) {
        let pointsPoly = [], obj = {}, insideCheck = [];
        let zonePolygonArr = zonePolygon[j].split(",");
        obj.name = zoneName[j];
        obj.seq = zoneSeq[j];
        for (let i = 0; i < zonePolygonArr.length - 1; i++) {
            let pathPoints = zonePolygonArr[i].split(" ");
            // pointsPoly[i] = {'x': parseFloat(pathPoints[0]), 'y': parseFloat(pathPoints[1])};
            pointsPoly[i] = new Point(pathPoints[0], pathPoints[1]);
            insideCheck[i] = new Point(pathPoints[0], pathPoints[1]);
            obj.points = pointsPoly;
            insideCheck.push(obj.points[0]);
        }
        obj.type = 'polygon';
        obj.coordinate = 'wgs84';
        obj.options = polygonStyle;
        areas.push(obj);
        polygonPoints.push(insideCheck);
    }
    console.log(polygonPoints);
    return areas;
}

drawingPolygon(getPolygonData());

