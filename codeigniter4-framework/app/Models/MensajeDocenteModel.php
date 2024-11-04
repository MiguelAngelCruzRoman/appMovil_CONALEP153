<?php

namespace App\Models;

use CodeIgniter\Model;

class MensajeDocenteModel extends Model
{
    protected $DBGroup          = 'default';
    protected $table            = 'mensaje_docente';
    protected $primaryKey       = 'id_mensajeDocente';
    protected $useAutoIncrement = true;
    protected $returnType       = 'array';
    protected $useSoftDeletes   = false;
    protected $protectFields    = true;
    protected $allowedFields    = ['id_docente','id_alumno','fecha','contenido'];

    // Dates
    protected $useTimestamps = false;
    protected $dateFormat    = 'datetime';
    protected $createdField  = 'created_at';
    protected $updatedField  = 'updated_at';
    protected $deletedField  = 'deleted_at';

    // Validation
    protected $validationRules = [
        'id_docente' => 'required|integer|exists[usuario.id_usuario]',
        'id_alumno' => 'required|integer|exists[usuario.id_usuario]',
        'fecha' => 'required|valid_date[Y-m-d]',
        'contenido' => 'required'
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
