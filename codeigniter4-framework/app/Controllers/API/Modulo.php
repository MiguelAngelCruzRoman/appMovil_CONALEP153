<?php

namespace App\Controllers\API;

use App\Models\ModuloModel;
use CodeIgniter\RESTful\ResourceController;
use Exception;

class Modulo extends ResourceController
{
    public function __construct(){
        $this->model = $this->setModel(new ModuloModel());
    }

    public function index(){
        $limit = $this->request->getGet('limit');
        $limit = is_numeric($limit) && $limit > 0 ? (int)$limit : 10; 
        $modulos = $this->model->findAll($limit);
        
        return $this->respond($modulos);
    }   
    
    public function create(){
        try {
            $modulo = $this->request->getJSON();
            
            if ($this->model->insert($modulo)) {
                $modulo->id_modulo = $this->model->insertID();
                return $this->respondCreated($modulo);
            } else {
                return $this->failServerError($this->model->validation->listErrors());
            }
            
        } catch (\Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
        }
    }

    public function edit($id = null){
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido.");
            }

            $modulo = $this->model->find($id);

            if ($modulo === null) {
                return $this->failNotFound("No se ha encontrado el módulo con el ID: ". $id);
            }

            return $this->respond($modulo);
        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
        }
    }

    public function update($id = null){
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido.");
            }

            $moduloVerificado = $this->model->find($id);

            if ($moduloVerificado === null) {
                return $this->failNotFound("No se ha encontrado el módulo con el ID: ". $id);
            }

            $modulo = $this->request->getJSON();

            if ($this->model->update($id, $modulo)) {
                $modulo->id_modulo = $id;
                return $this->respondUpdated($modulo);
            } else {
                return $this->failValidationError($this->model->validation->listErrors());
            }

        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
        }
    }

    public function delete($id = null){
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido.");
            }

            $moduloVerificado = $this->model->find($id);

            if ($moduloVerificado === null) {
                return $this->failNotFound("No se ha encontrado el módulo con el ID: ". $id);
            }

            if ($this->model->delete($id)) {
                return $this->respondDeleted($moduloVerificado);
            } else {
                return $this->failServerError("No se ha podido eliminar el registro.");
            }

        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
        }
    }

    public function getModulosPorUsuario($id_usuario = null)
{
    try {
        if ($id_usuario === null) {
            return $this->failValidationError("No se ha pasado un ID de usuario válido.");
        }

        $limit = $this->request->getGet('limit');
        $limit = is_numeric($limit) && $limit > 0 ? (int)$limit : 10;

        $diaSemana = $this->request->getGet('diaSemana');
        if ($diaSemana === null) {
            $diaSemana = strtoupper(date('D')); 
            $dias = [
                'Mon' => 'LUN', 
                'Tue' => 'MAR', 
                'Wed' => 'MIE', 
                'Thu' => 'JUE', 
                'Fri' => 'VIE', 
                'Sat' => 'SAB', 
                'Sun' => 'DOM'
            ];
            $diaSemana = $dias[$diaSemana] ?? strtoupper(date('D'));
        }

        $db = \Config\Database::connect();

        $query = "SELECT 
                    modulo.tipoFormacion, 
                    modulo.horasClase, 
                    modulo.nombreModulo, 
                    modulo.diaSemana,
                    CONCAT(docente.primerNombre, ' ', 
                           IFNULL(docente.segundoNombre, ''), ' ', 
                           docente.apellidoPaterno, ' ', 
                           docente.apellidoMaterno) AS nombreDocente,
                    modulo.salonClase
                  FROM 
                    modulo_grupo
                  JOIN 
                    modulo ON modulo.id_modulo = modulo_grupo.id_modulo
                  JOIN 
                    usuario AS docente ON modulo.id_docente = docente.id_usuario
                  JOIN 
                    grupo ON grupo.id_grupo = modulo_grupo.id_grupo
                  JOIN 
                    grupo_usuario ON grupo.id_grupo = grupo_usuario.id_grupo
                  WHERE 
                    grupo_usuario.id_usuario = ? 
                    AND modulo.diaSemana = ?
                  LIMIT ?";

        $result = $db->query($query, [$id_usuario, $diaSemana, $limit])->getResult();

        if (empty($result)) {
            $diaSemanaActual = strtoupper(date('D'));
            $diaSemanaActual = $dias[$diaSemanaActual] ?? strtoupper(date('D')); 

            $result = $db->query($query, [$id_usuario, $diaSemanaActual, $limit])->getResult();
        }

        if (empty($result)) {
            return $this->failNotFound("No se han encontrado módulos para el usuario con ID: ". $id_usuario);
        }

        return $this->respond($result);
    } catch (\Exception $e) {
        return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
    }
}

public function getModulosPorModuloUsuario($id_usuario = null)
{
    try {
        if ($id_usuario === null) {
            return $this->failValidationError("No se ha pasado un ID de usuario válido.");
        }

        $limit = $this->request->getGet('limit');
        $limit = is_numeric($limit) && $limit > 0 ? (int)$limit : 10;


        $db = \Config\Database::connect();

        $query = "SELECT 
                    docente.id_usuario as id_docente,
                    modulo.nombreModulo AS modulo, 
                    CONCAT(docente.primerNombre, ' ', 
                           IFNULL(docente.segundoNombre, ''), ' ', 
                           docente.apellidoPaterno, ' ', 
                           docente.apellidoMaterno) AS nombre,
                    docente.foto 
                  FROM 
                    modulo_grupo
                  JOIN 
                    modulo ON modulo.id_modulo = modulo_grupo.id_modulo
                  JOIN 
                    usuario AS docente ON modulo.id_docente = docente.id_usuario
                  JOIN 
                    grupo ON grupo.id_grupo = modulo_grupo.id_grupo
                  JOIN 
                    grupo_usuario ON grupo.id_grupo = grupo_usuario.id_grupo
                  WHERE 
                    grupo_usuario.id_usuario = ? 
                  LIMIT ?";

        $result = $db->query($query, [$id_usuario, $limit])->getResult();

        if (empty($result)) {
            $diaSemanaActual = strtoupper(date('D'));
            $diaSemanaActual = $dias[$diaSemanaActual] ?? strtoupper(date('D')); 

            $result = $db->query($query, [$id_usuario, $diaSemanaActual, $limit])->getResult();
        }

        if (empty($result)) {
            return $this->failNotFound("No se han encontrado docentes para el usuario con ID: ". $id_usuario);
        }

        return $this->respond($result);
    } catch (\Exception $e) {
        return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
    }
}

public function getSiguientesModulos($id_usuario = null)  
{
    try {
        if ($id_usuario === null) {
            return $this->failValidationError("No se ha pasado un ID de usuario válido.");
        }

        $limit = $this->request->getGet('limit');
        $limit = is_numeric($limit) && $limit > 0 ? (int)$limit : 2;

        $db = \Config\Database::connect();

        $zonaHoraria = new \DateTimeZone('America/Mexico_City');
        $horaActual = (new \DateTime('now', $zonaHoraria))->format('H:i');

        $query = "SELECT 
                    modulo.horasClase, 
                    modulo.nombreModulo
                  FROM 
                    modulo_grupo
                  JOIN 
                    modulo ON modulo.id_modulo = modulo_grupo.id_modulo
                  JOIN 
                    usuario AS docente ON modulo.id_docente = docente.id_usuario
                  JOIN 
                    grupo ON grupo.id_grupo = modulo_grupo.id_grupo
                  JOIN 
                    grupo_usuario ON grupo.id_grupo = grupo_usuario.id_grupo
                  WHERE 
                    grupo_usuario.id_usuario = ? 
                    AND modulo.diaSemana = ?
                  LIMIT ?";

        $diaSemana = $this->request->getGet('diaSemana');
        if ($diaSemana === null) {
            $diaSemana = strtoupper(date('D')); 
            $dias = [
                'Mon' => 'LUN', 
                'Tue' => 'MAR', 
                'Wed' => 'MIE', 
                'Thu' => 'JUE', 
                'Fri' => 'VIE', 
                'Sat' => 'SAB', 
                'Sun' => 'DOM'
            ];
            $diaSemana = $dias[$diaSemana] ?? strtoupper(date('D'));
        }

        $result = $db->query($query, [$id_usuario, $diaSemana, $limit])->getResult();

        $modulosFiltrados = array_filter($result, function($modulo) use ($horaActual) {
            [$horaInicio] = explode(' - ', $modulo->horasClase); 
            return $horaInicio > $horaActual; 
        });

        $modulosFinales = array_values($modulosFiltrados);
        $faltantes = $limit - count($modulosFinales);

        for ($i = 0; $i < $faltantes; $i++) {
            $modulosFinales[] = (object)[
                'horasClase' => '++:++ - ++:++',
                'nombreModulo' => 'SIN CLASES'
            ];
        }

        return $this->respond($modulosFinales); 
    } catch (\Exception $e) {
        return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
    }
}



}
