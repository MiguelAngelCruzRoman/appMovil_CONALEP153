<?php

namespace App\Models;

use CodeIgniter\Model;

class ModuloModel extends Model
{
    protected $DBGroup          = 'default';
    protected $table            = 'modulo';
    protected $primaryKey       = 'id_modulo';
    protected $useAutoIncrement = true;
    protected $returnType       = 'array';
    protected $useSoftDeletes   = false;
    protected $protectFields    = true;
    protected $allowedFields    = ['id_docente','tipoFormación','horasClase','nombreModulo','salonClase','diaSemana'];

    // Dates
    protected $useTimestamps = false;
    protected $dateFormat    = 'datetime';
    protected $createdField  = 'created_at';
    protected $updatedField  = 'updated_at';
    protected $deletedField  = 'deleted_at';

    // Validation
    protected $validationRules      = [
        'id_docente'   => 'required|is_natural_no_zero', 
        'tipoFormacion'=> 'required|in_list[DISCIPLINAR BASICA,PROFESIONAL,TRAYECTO TECNICO]', 
        'horasClase'   => 'required|regex_match[/^([01]?[0-9]|2[0-3]):([0-5][0-9]) (AM|PM) - ([01]?[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$/]', 
        'nombreModulo' => 'required|string|max_length[250]', 
        'salonClase'   => 'required|string|max_length[250]', 
        'diaSemana'=> 'required|in_list[LUN,MAR,MIE,JUE,VIE]', 
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
