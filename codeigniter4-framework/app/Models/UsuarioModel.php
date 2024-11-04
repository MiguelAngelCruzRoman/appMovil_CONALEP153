<?php

namespace App\Models;

use CodeIgniter\Model;

class UsuarioModel extends Model
{
    protected $DBGroup          = 'default';
    protected $table            = 'usuario';
    protected $primaryKey       = 'id_usuario';
    protected $useAutoIncrement = true;
    protected $returnType       = 'array';
    protected $useSoftDeletes   = false;
    protected $protectFields    = true;
    protected $allowedFields    = ['primerNombre', 'segundoNombre', 'apellidoPaterno', 'apellidoMaterno', 'username', 'password', 'foto', 'rol',
    ];

    // Dates
    protected $useTimestamps = false;
    protected $dateFormat    = 'datetime';
    protected $createdField  = 'created_at';
    protected $updatedField  = 'updated_at';
    protected $deletedField  = 'deleted_at';

    // Validation
    protected $validationRules = [
        'primerNombre' => 'required|max_length[50]',
        'segundoNombre' => 'max_length[50]', 
        'apellidoPaterno' => 'required|max_length[50]',
        'apellidoMaterno' => 'required|max_length[50]',
        'username' => 'required|max_length[50]|is_unique[usuario.username]', 
        'password' => 'required|max_length[50]',
        'foto' => 'required',
        'rol' => 'required|in_list[DOCENTE,ADMINISTRATIVO,ALUMNO]'
    ];
    protected $validationMessages   = [];
    protected $skipValidation       = false;
    protected $cleanValidationRules = true;

    // Callbacks
    protected $allowCallbacks = true;
    protected $beforeInsert   = [];
    protected $afterInsert    = [];
    protected $beforeUpdate   = [];
    protected $afterUpdate    = [];
    protected $beforeFind     = [];
    protected $afterFind      = [];
    protected $beforeDelete   = [];
    protected $afterDelete    = [];
}
