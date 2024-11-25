<?php

namespace App\Controllers\API;

use App\Models\UsuarioModel;
use CodeIgniter\RESTful\ResourceController;
use Exception;

class Usuario extends ResourceController
{

    public function __construct()
    {
        $this->model = $this->setModel(new UsuarioModel());
    }


    public function index()
    {
        $usuarios = $this->model->findAll();
        return $this->respond($usuarios);
    }

    public function create()
    {
        try {
            $usuario = $this->request->getJSON();
            if ($this->model->insert($usuario)) {
                $usuario->id = $this->model->insertID();
                return $this->respondCreated($usuario);
            } else {
                return $this->failServerError($this->model->validation->listErrors());
            }
        } catch (\Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor");
        }
    }

    public function edit($id = null)
    {
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido");
            }

            $usuario = $this->model->find($id);

            if ($usuario === null) {
                return $this->failNotFound("No se ha encontrado el usuario con el ID: " . $id);
            }

            return $this->respond($usuario);
        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor");
        }
    }


    public function update($id = null)
    {
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido");
            }

            $usuarioVerificado = $this->model->find($id);

            if ($usuarioVerificado === null) {
                return $this->failNotFound("No se ha encontrado el usuario con el ID: " . $id);
            }

            $usuario = $this->request->getJSON();

            if ($this->model->update($id, $usuario)) {
                $usuario->id = $id;
                return $this->respondUpdated($usuario);
            } else {
                return $this->failValidationError($this->model->validation->listErrors());
            }
        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor");
        }
    }


    public function delete($id = null)
    {
        try {
            if ($id === null) {
                return $this->failValidationError("No se ha pasado un ID válido");
            }

            $usuarioVerificado = $this->model->find($id);

            if ($usuarioVerificado === null) {
                return $this->failNotFound("No se ha encontrado la usuario con el ID: " . $id);
            }

            if ($this->model->delete($id)) {
                return $this->respondDeleted($usuarioVerificado);
            } else {
                return $this->failServerError("No se ha podido eliminar el registro");
            }
        } catch (Exception $e) {
            return $this->failServerError(description: "Ha ocurrido un error en el servidor");
        }
    }


    public function login()
    {
        try {
            $credentials = $this->request->getJSON();

            $username = $credentials->username ?? null;
            $password = $credentials->password ?? null;

            if ($username === null || $password === null) {
                return $this->failValidationError("Se requieren username y password.");
            }

            $usuario = $this->model->where('username', $username)->first();

            if ($usuario['password'] !=  $password) {
                return $this->failUnauthorized("Username o password incorrectos.");
            }

            return $this->respond([
                'id_usuario' => $usuario['id_usuario'],
                'nombre' => trim($usuario['primerNombre'] . ' ' . ($usuario['segundoNombre'] ?? '') . ' ' . $usuario['apellidoPaterno'] . ' ' . $usuario['apellidoMaterno']),
                'rol' => $usuario['rol'],
                'foto' => $usuario['foto'],
            ]);
        } catch (Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor");
        }
    }

    public function getGruposPorUsuario($id_usuario = null)
    {
        try {
            if ($id_usuario === null) {
                return $this->failValidationError("No se ha pasado un ID de usuario válido.");
            }

            $db = \Config\Database::connect();

            $query = "SELECT 
                        grupo.nombre, 
                        grupo.imagen as imagenGrupo, 
	                    usuario.username,
                        usuario.id_usuario,
                        usuario.rol as rolUsuario,
                        usuario.foto as imagenUsuario
	                from 
                        grupo 
	                join 
                        grupo_usuario on grupo.id_grupo = grupo_usuario.id_grupo
                    join 
                        usuario on grupo_usuario.id_usuario = usuario.id_usuario
                    where 
                        usuario.id_usuario = ?";

            $result = $db->query($query, [$id_usuario])->getResult();

            if (empty($result)) {
                return $this->failNotFound("No se han encontrado grupos para el usuario con ID: " . $id_usuario);
            }

            return $this->respond($result);
        } catch (\Exception $e) {
            return $this->failServerError("Ha ocurrido un error en el servidor: " . $e->getMessage());
        }
    }
}
