<?php

namespace App\Controllers\API;

use App\Models\ActividadModel;
use CodeIgniter\RESTful\ResourceController;
use Exception;

class Actividad extends ResourceController
{

    public function __construct(){
        $this->model = $this->setModel(new ActividadModel());
    }


    public function index(){
        $actividades = $this->model->findAll();
        return $this->respond($actividades);
    }   
    
    public function create(){
        try {
            $actividad = $this->request->getJSON();
                if($this->model->insert($actividad)){
                    $actividad->id = $this->model->insertID();
                    return $this->respondCreated($actividad);
                }else{
                    return $this->failServerError($this->model->validation->listErrors());
                }
            
        } catch (\Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor");
        }
    }

     public function edit($id = null){
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido");
            }

            $actividad = $this->model->find($id);

            if ($actividad === null) {
                return $this->failNotFound("No se ha encontrado la actividad con el ID: ". $id);
            }

            return $this->respond($actividad);
        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor");
        }
    }


    public function update($id = null){
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido");
            }

            $actividadVerificado = $this->model->find($id);

            if ($actividadVerificado === null) {
                return $this->failNotFound("No se ha encontrado la actividad con el ID: ". $id);
            }

            $actividad = $this->request->getJSON();

            if($this->model->update($id, $actividad)){
                $actividad->id = $id;
                return $this->respondUpdated($actividad);
            }else{
                return $this->failValidationError($this->model->validation->listErrors());
            }

        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor");
        }
    }


    public function delete($id = null){
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido");
            }

            $actividadVerificado = $this->model->find($id);

            if ($actividadVerificado === null) {
                return $this->failNotFound("No se ha encontrado la actividad con el ID: ". $id);
            }

            if($this->model->delete($id)){
                return $this->respondDeleted($actividadVerificado);
            }else{
                return $this->failServerError("No se ha podido eliminar el registro");
            }

        } catch (Exception $e) {
            return $this->failServerError(description: "Ha ocurrido un error en el servidor");
        }
    }
}
