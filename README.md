<p align="center"><a href="https://laravel.com" target="_blank"><img src="https://raw.githubusercontent.com/laravel/art/master/logo-lockup/5%20SVG/2%20CMYK/1%20Full%20Color/laravel-logolockup-cmyk-red.svg" width="400"></a></p>

<p align="center">
<a href="https://travis-ci.org/laravel/framework"><img src="https://travis-ci.org/laravel/framework.svg" alt="Build Status"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/dt/laravel/framework" alt="Total Downloads"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/v/laravel/framework" alt="Latest Stable Version"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/l/laravel/framework" alt="License"></a>
</p>

## Sobre o SRealização
Sistema de cadastro de inscrições do maior evento ciclistico da região norte

## Comandos para iniciar o projeto
* composer install
* npm install
* editar o arquivo .env com os dados corretos
* rodar o comando php artisan key:generate

## Antes de por em produção deve-se rodar o comando para compilar fonts do vuejs
* npm run watch 
* npm run build (este comando depende do npm run watch rodando)
* desabilitar APP_DEBUG=true do .env
* Rodar o comando php artisan config:clear sempre ao alterar o env


## Executar a aplicação em desenvolvimento
*  php artisan serve
*  npm run watch (alguns momentos é necessário sair e entrar nesse comando para recompilar corretamente)


## License

The Laravel framework is open-sourced software licensed under the [MIT license](https://opensource.org/licenses/MIT).
