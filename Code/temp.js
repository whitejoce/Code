var jquery = document.createElement('script');
jquery.src = 'https://cdn.staticfile.org/jquery/3.1.1/jquery.min.js';
//jquery.src = 'https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js';
document.getElementsByTagName('head')[0].appendChild(jquery);

for (var i = 1; i <= 40; i++) 
 { 
    var t = document.getElementById('tmjo_' + String(i)); 
    var s = document.getElementsByName('tmda_'+ String(i)); 
    for (let j of s) { 
        if (j.value===t.value){
            $(j.parentElement).click();
        }
    };
 }
