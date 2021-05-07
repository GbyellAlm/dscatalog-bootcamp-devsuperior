import React from 'react';
import './styles.scss';
import { Link, NavLink } from 'react-router-dom'

const Navbar = () => (
    // "bg-primary" eh o azul padrao do Bootstrap. Personalisei esse azul padrao pra outro tom de azul no arquivo "custom.scss".
    <nav className="row bg-primary main-nav">
        <div className="col-2">
            <Link to="/" className="nav-logo-text">
                <h4>DS Catalog</h4>
            </Link>
        </div>
        <div className="col-6 offset-2">
            <ul className="main-menu">
                <li>
                    <NavLink to="/" exact>HOME</NavLink>
                </li>
                <li>
                    <NavLink to="/products">CATÁLAGO</NavLink>
                </li>
                <li>
                    <NavLink to="/admin">ADMIN</NavLink>
                </li>
            </ul>
        </div>
    </nav>
)

export default Navbar;
