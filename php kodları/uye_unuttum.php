<?php

$uye_mail = (htmlspecialchars($_GET['unuttumMail']));

include './baglan.php';
mysql_set_charset('utf8mb4');

$sqlsorgu = mysql_query("select * from kullanici_table where uye_mail='".$uye_mail."'");
$sonuc = mysql_fetch_assoc($sqlsorgu);

$mail_icerik = "";
$mail_icerik .= "Kitap Uygulamasý için bilgileriniz : "."\n";
$mail_icerik .= "mail adresiniz : ".(string)$sonuc['uye_mail']."\n";
$mail_icerik .= "adýnýz : ".(string)$sonuc['uye_adi']."\n";
$mail_icerik .= "soyadýnýz : ".(string)$sonuc['uye_soyad']."\n";
$mail_icerik .= "þifreniz : ".(string)$sonuc['uye_sifre']."\n";
$mail_icerik .= "üyelik tarihi : ".(string)$sonuc['uye_uyelik_tarih']."\n";

$alici = (string)$sonuc['uye_mail'];
$konu = 'Bilgilendirme';

$gonderici = "from: feyyazfy@gmail.com\r\n";
$gonderici .= "reply-to: feyyazfy@gmail.com\r\n";

if($sqlsorgu && mysql_num_rows($sqlsorgu)>0 && mail($alici, $konu, $mail_icerik, $gonderici)){
echo '{"durum":1}';
}
else if(!$sqlsorgu || mysql_num_rows($sqlsorgu)==0){
echo '{"durum":0}';
}


?>