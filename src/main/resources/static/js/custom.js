 $(document).ready(function () {
    function create(message) {
            let str = '<div class="modal fade" id="action" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">' +
                '<div class="modal-dialog" role="document">' +
                '<div class="modal-content">' +
                '<div class="modal-header">' +
                '<h5 class="modal-title">Confirmation d\'action</h5>' +
                '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
                '<span aria-hidden="true">&times;</span>' +
                '</button>' +
                '</div>' +
                '<div class="modal-body">' +
                message +
                '</div>' +
                '<div class="modal-footer">' +
                '<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>' +
                '<button type="button" class="btn btn-primary" id="confirm">Confirmer</button>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';

            return str;
        }


    $(document).on("click", '.supp', function () {
                var message = "Confirmer la suppression ?";
                var url = $(this).attr('href');
                $('body').append(create(message));
                $('#action').modal('show');
                $('#confirm').on('click', function () {
                    window.location = url;
                })
    });
 });

 $(document).ready(function(){

             // check paragraph once toggle effect is completed
             if($("p").is(":visible")){
                 setTimeout(function() {
                                     $('p').fadeToggle('slow');
                                 }, 2000);
             } else{

             }
 });

