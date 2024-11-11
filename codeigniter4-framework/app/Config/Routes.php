<?php

use CodeIgniter\Router\RouteCollection;

/**
 * @var RouteCollection $routes
 */
$routes->get('/', 'Home::index');

$routes->group('api', ['namespace' => 'App\Controllers\API'], function($routes){
    $routes->get('actividad', 'Actividad::index');
    $routes->post('actividad/create', 'Actividad::create');
    $routes->get('actividad/edit/(:num)', 'Actividad::edit/$1');
    $routes->put('actividad/update/(:num)', 'Actividad::update/$1');
    $routes->delete('actividad/delete/(:num)', 'Actividad::delete/$1');
    $routes->get('actividad/usuario/(:num)', 'Actividad::getActividadesPorUsuario/$1');


    $routes->get('usuario', 'Usuario::index');
    $routes->post('usuario/create', 'Usuario::create');
    $routes->get('usuario/edit/(:num)', 'Usuario::edit/$1');
    $routes->put('usuario/update/(:num)', 'Usuario::update/$1');
    $routes->delete('usuario/delete/(:num)', 'Usuario::delete/$1');
    $routes->post('usuario/login', 'Usuario::login');
});
