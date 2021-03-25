let selectedOption;
$('[name="select-option"]').each(function(index){
    if(index==0){
        selectedOption = $(this).val();
        $('div .'+selectedOption).show();
    }
});
$('select[name="option"]').on('change',function(){
    $('div .'+selectedOption).hide();
    selectedOption = $(this).find(":selected").val();
    $('div .'+selectedOption).show();
});
function toCart(){

}