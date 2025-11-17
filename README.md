# 5027231068_subnet_Jarkom-A
## Router HQ
enable
configure terminal
hostname R-HQ

## Subnet Sekretariat
interface gigabitEthernet0/0
 description Link_to_Sekretariat
 ip address 10.92.0.1 255.255.254.0
 no shutdown

## Subnet Kurikulum
interface gigabitEthernet0/1
 description Link_to_Kurikulum
 ip address 10.92.2.1 255.255.255.0
 no shutdown

## Subnet Guru & Tendik
interface gigabitEthernet0/2
 description Link_to_GuruTendik
 ip address 10.92.3.1 255.255.255.128
 no shutdown

## Subnet Sarpras
interface gigabitEthernet0/3
 description Link_to_Sarpras
 ip address 10.92.3.129 255.255.255.192
 no shutdown

## Subnet Server & Admin
interface gigabitEthernet0/4
 description Link_to_ServerAdmin
 ip address 10.92.3.225 255.255.255.248
 no shutdown

## Link ke Router Branch
interface gigabitEthernet0/5
 description Link_to_RouterBranch
 ip address 10.92.3.233 255.255.255.252
 no shutdown

## Static Route ke LAN Cabang
ip route 10.92.3.192 255.255.255.224 10.92.3.234

end
write memory

## Router BR
enable
configure terminal
hostname R-BR

## LAN Pengawas Sekolah (Cabang)
interface fastEthernet0/0
 description Link_to_PengawasCabang
 ip address 10.92.3.193 255.255.255.224
 no shutdown

## Link ke Router HQ
interface fastEthernet0/1
 description Link_to_R-HQ
 ip address 10.92.3.234 255.255.255.252
 no shutdown

## Static Route ke seluruh jaringan HQ
ip route 10.92.0.0 255.255.252.0 10.92.3.233

end
write memory
