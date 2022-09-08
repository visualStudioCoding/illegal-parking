<!DOCTYPE html>
<html lang="ko">
<style>
    .map_wrap {width: 100%;position: relative;}
    .modes {position: absolute;top: 10px;left: 10px;z-index: 1;}
    .getdata{position: absolute;top: 370px;left: 10px;z-index: 1;}
    #drawingMap, #map {width: 100%;height: 600px;}
    #map {margin-top: 10px;}

    #timeRow {display: none;}
</style>
<body>
<#include "*/common/navigation.ftl">
<!-- 본문 시작 -->
<div class="map_wrap">
    <div id="drawingMap"></div>
    <p class="modes">
        <button onclick="selectOverlay('POLYGON')">구역추가</button>
        <button onclick="getDataFromDrawingMap()">저장</button>

        <span>
            <label><input type="radio" name="searchZoneType" value="" checked>전체</label>
            <label><input type="radio" name="searchZoneType" value="N">주정차 불가</label>
            <label><input type="radio" name="searchZoneType" value="Y">탄력적 가능</label>
            <label><input type="radio" name="searchZoneType" value="F">5분간 가능</label>
        </span>
    </p>
</div>
    <p id="result"></p>

<div class="modal fade" id="areaSettingModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="formAreaSetting" name="formAreaSetting">
                <input type="hidden" id="polySeq" name="polySeq" value=""/>
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">구역설정</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="zoneType" id="zone1" value="N">
                        <label class="form-check-label" for="zone1">불가</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="zoneType" id="zone2" value="Y">
                        <label class="form-check-label" for="zone2">탄력적 가능</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="zoneType" id="zone3" value="F">
                        <label class="form-check-label" for="zone3">5분간 가능</label>
                    </div>

                    <div class="row" id="timeRow">
                        <div class="col">
                            <label for="inputState">시작</label>
                            <select class="form-select" id="startTime" name="startTime" aria-label="Default select example" disabled>
                                <#list 1..24 as time>
                                    <option value="${time}">${time}시</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col">
                            <label for="inputState">종료</label>
                            <select class="form-select" id="endTime" name="endTime" aria-label="Default select example" disabled>
                                <#list 1..24 as time>
                                    <option value="${time}">${time}시</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="btnDelete">삭제</button>
                    <button type="button" class="btn btn-primary" id="btnUpdate">확인</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    let zoneSeq = [];
    let zoneType = [];
    let zonePolygon = [];
    let zoneArea = [];
    <#list polygonList as polygon>
        zoneSeq.push("${polygon.zoneSeq}");
        zoneType.push("${polygon.zoneType}");
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