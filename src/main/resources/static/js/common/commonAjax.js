/**
 * commonAjax 공통모듈
 * ------------------
 */

let commonAjax = function(url, fn, methodType, param, errorMsg){
    // 데이터 값이 잘 넘어왔는지 확인
    // console.log("url : ", url);
    // console.log("data : ", param);
    // console.log("methodType : ", methodType);
    // console.log("errorMsg : ", errorMsg);

    let request = $.ajax({
        url: url,
        method: methodType,
        data: param,
        dataType: "json"
    });
    //콜백함수
    request.done(fn);
    console.log(fn);

    request.fail(function( jqXHR, textStatus ) {
        alert( textStatus + " : " + errorMsg );
    });
}