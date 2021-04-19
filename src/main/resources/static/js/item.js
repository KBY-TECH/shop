let selectedOption;
$('[name="select-option"]').each(function(index){
    if(index==0){
        selectedOption = $(this).val();
        console.log(selectedOption);
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


$('a[href="#Description"]').on('click',function(event){
    event.preventDefault();
    $('.in.active').removeClass('in active');
    $('div .Description').addClass('in active');
})

$('a[href="#Information"]').on('click',function(event){
    event.preventDefault();
    $('.in.active').removeClass('in active');
    $('div .Information').addClass('in active');
});
$('a[href="#Reviews"]').on('click',function(event){
    event.preventDefault();
    $('.in.active').removeClass('in active');
    $('div .Reviews').addClass('in active');
});

