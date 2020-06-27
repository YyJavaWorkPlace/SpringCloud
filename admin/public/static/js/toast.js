Toast = {
    success:function(message){
        Swal.fire({
            icon: 'success',
            title: message,
            position:'top-end',
            showConfirmButton: false,
            timer:3000
        });
    },
    error:function(message){
        Swal.fire({
            icon: 'error',
            title: message,
            position:'top-end',
            showConfirmButton: false,
            timer:3000
        });
    },
    warning:function(message){
        Toast.fire({
            icon: 'warning',
            title: message,
            position:'top-end',
            showConfirmButton: false,
            timer:3000
        });
    }
};