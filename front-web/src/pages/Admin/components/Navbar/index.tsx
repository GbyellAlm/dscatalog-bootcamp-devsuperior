import React from 'react';
import './styles.scss';

const Navbar = () => (
    <nav className="admin-nav-container">
        <ul>
            <li>
                <a href="#" className="admin-nav-item active">Meus Produtos</a>
            </li>
            <li>
                <a href="#" className="admin-nav-item">Minhas Categorias</a>
            </li>
            <li>
                <a href="#" className="admin-nav-item">Meus Usuarios</a>
            </li>
        </ul>
    </nav>
);

export default Navbar;