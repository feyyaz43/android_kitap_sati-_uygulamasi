<?php

$uye_mail = (htmlspecialchars($_GET['girisMail']));
$uye_sifre = (htmlspecialchars($_GET['girisSifre']));

include './baglan.php';
mysql_set_charset('utf8mb4');

$sqlsorgu = mysql_query("select * from kullanici_table where uye_mail='".$uye_mail."'");
$sonuc = mysql_fetch_assoc($sqlsorgu);

$uye_id_veri = (int)$sonuc['uye_id'];
$uye_mail_veri = (string)$sonuc['uye_mail'];
$uye_sifre_veri = (string)$sonuc['uye_sifre'];

if($sqlsorgu && mysql_num_rows($sqlsorgu)>0 && $uye_mail_veri==$uye_mail && $uye_sifre_veri==$uye_sifre){
	echo '{"durum":1 , "uye_id":'.$uye_id_veri.'}';
}
else{
	echo '{"durum":0}';
}


?>