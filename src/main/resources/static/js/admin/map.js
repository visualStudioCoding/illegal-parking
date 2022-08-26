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
        kakao.maps.drawing.OverlayType.POLYGON
    ],
    // 사용자에게 제공할 그리기 가이드 툴팁입니다
    // 사용자에게 도형을 그릴때, 드래그할때, 수정할때 가이드 툴팁을 표시하도록 설정합니다
    guideTooltip: ['draw', 'drag', 'edit'],
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
var mapContainer = document.getElementById('map'),
    mapOptions = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

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

    // 아래 지도에 그려진 도형이 있다면 모두 지웁니다
    removeOverlays();

    let param = data[kakao.maps.drawing.OverlayType.POLYGON];

    let errorMsg = '실패';
    let callBackFn = function (data) {
        if (param.length === 0) {
            alert('구역을 설정하시기 바랍니다.');
            return false;
        } else {
            // 지도에 가져온 데이터로 도형들을 그립니다
            drawingPolygon(param, 'drawing');
            // 생성한 폴리곤 삭제
            removeDrawingOverlays();
        }
    }
    commonAjax("/admin/map/polygonInsert", callBackFn, 'POST', JSON.stringify(param), errorMsg);

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

function drawingPolygon(polygons, stat) {
    let areas = getPolygonData();
    if(stat === 'drawing') {
        areas.forEach(function (element) {
            polygons.push(element);
        })
    }
    var len = polygons.length, i = 0;
    removeOverlays();

    for (; i < len; i++) {
        var path = pointsToPath(polygons[i].points);
        var style = polygons[i].options;
        var polygon = new kakao.maps.Polygon({
            map: drawingMap,
            path: path,
            strokeColor: style.strokeColor,
            strokeOpacity: style.strokeOpacity,
            strokeStyle: style.strokeStyle,
            strokeWeight: style.strokeWeight,
            fillColor: style.fillColor,
            fillOpacity: style.fillOpacity
        });
        overlays.push(polygon);

    }
}
kakao.maps.event.addListener(drawingMap, 'click', function(mouseEvent) {
    var latlng = mouseEvent.latLng;
    console.log('click! ' + latlng.toString());
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
        let pointsPoly = [], obj = {};
        let zonePolygonArr = zonePolygon[j].split(",");
        obj.name = zoneName[j];
        obj.seq = zoneSeq[j];
        for (let i = 0; i < zonePolygonArr.length - 1; i++) {
            let pathPoints = zonePolygonArr[i].split(" ");
            pointsPoly[i] = {'x': parseFloat(pathPoints[0]), 'y': parseFloat(pathPoints[1])};
            obj.points = pointsPoly;
        }
        obj.type = 'polygon';
        obj.coordinate = 'wgs84';
        obj.options = polygonStyle;
        areas.push(obj);
    }
    return areas;
}

drawingPolygon(getPolygonData(), 'load');