<?php
    $database = 'samet_feyyaz';
    $host = '95.211.242.157';
    $dbuser = 'feyyaz';
    $dbpass = 'feyyaz2015';
    $baglan = @mysql_connect($host,$dbuser,$dbpass);
    if(! $baglan) die ("Mysql Baglantisi Yapilamadi");
    @mysql_select_db($database,$baglan) or die ("Veri Tabanina Baglanti Yapilamadi");
?>