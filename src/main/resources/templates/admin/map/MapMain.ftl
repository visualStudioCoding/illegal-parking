<!DOCTYPE html>
<html lang="ko">
<style>
    .map_wrap {width: 100%;position: relative;}
    .modes {position: absolute;top: 10px;left: 10px;z-index: 1;}
    .getdata{position: absolute;top: 370px;left: 10px;z-index: 1;}
    #drawingMap, #map {width: 100%;height: 600px;}
    #map {margin-top: 10px;}
</style>
<body>
<#include "*/common/navigation.ftl">
<!-- 본문 시작 -->
<div class="map_wrap">
    <div id="drawingMap"></div>
    <p class="modes">
        <button onclick="selectOverlay('POLYGON')">구역설정</button>
        <button onclick="getDataFromDrawingMap()">가져오기</button>
    </p>
</div>

<script type="text/javascript">
    let zoneSeq = [];
    let zoneName = [];
    let zonePolygon = [];
    let zoneArea = [];
    <#list polygonList as polygon>
        zoneSeq.push("${polygon.zoneSeq}");
        zoneName.push("${polygon.zoneName}");
        zonePolygon.push("${polygon.zonePolygon}");
    </#list>
</script>

<script src="/js/admin/map.js"></script>
<!-- /본문 끝 -->
<!-- 푸터 시작 -->
<#include "*/common/footer.ftl">
<!-- /푸터 끝 -->
</body>
</html>