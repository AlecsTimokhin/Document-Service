function deleteDocument(id, time, tr){

    $('div#message').html('');

    $.ajax({
        url: '/documents/' + id,
        cache: false,
        type: 'DELETE',
        timeout: time,
        success: function(data){
            var div = $('div#message');
            showMessage(div);
            div.html(data.message);
            div.addClass(data.status);
            tr.detach();
            reFreshDocumentData();
        },
        error: function(data){
            var div = $('div#message');
            showMessage(div);
            div.html('Ошибка удаления документа');
            div.addClass('error');
        },
        complete: function(){
            hideMessage();
        }
    });

}


$(document).ready(function(){

    $('#addDoc').on('submit', function(e){

        $('div#message').html('');

        e.preventDefault();
        var $that = $(this),
        formData = new FormData($that.get(0));

        $.ajax({
            url: '/documents/add',
            cache: false,
            type: 'POST',
            timeout: 3000,
            contentType: false,
            processData: false,
            //data: $('div#addDocument').find('form.AjaxForm').serialize(),
            data: formData,
            success: function(data) {
                var div = $('div#message');
                showMessage(div);
                div.html(data.message);
                div.addClass(data.status);
            },
            error: function(data) {
                var div = $('div#message');
                showMessage(div);
                div.html(data.responseJSON.message);
                div.addClass('error');
            },
            complete: function() {
                hideMessage();
            }
        });

    });

});


function showMessage(div) {
    div.stop(true);
    div.css("opacity", 1.0);
    div.removeClass();
    div.show();
}


function hideMessage() {
    var div = $('div#message');
    div.animate({
        opacity: 0.2,
    }, 2500, function() {
        div.hide(800);
        div.html('');
    });
}


function reFreshDocumentData(){
    var trArr = $('tr.documentTr');
    $('b#countDocuments').text( '' + trArr.length + '' );
    var i = 0;
    trArr.each(function(){
        i++;
        $(this).find('td.numberTd').text('' + i + '');
    });
}
