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
    $routes->get('grupo/usuario/(:num)', 'Usuario::getGruposPorUsuario/$1');

    $routes->get('modulo', 'Modulo::index'); 
    $routes->post('modulo/create', 'Modulo::create'); 
    $routes->get('modulo/edit/(:num)', 'Modulo::edit/$1'); 
    $routes->put('modulo/update/(:num)', 'Modulo::update/$1'); 
    $routes->delete('modulo/delete/(:num)', 'Modulo::delete/$1'); 
    $routes->get('modulo/usuario/(:num)', 'Modulo::getModulosPorUsuario/$1');
    $routes->get('docente/usuario/(:num)', 'Modulo::getModulosPorModuloUsuario/$1');
    $routes->get('modulo/siguiente/usuario/(:num)', 'Modulo::getSiguientesModulos/$1');
});
