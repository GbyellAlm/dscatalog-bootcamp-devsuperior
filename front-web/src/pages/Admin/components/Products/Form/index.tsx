import React from 'react'
import BaseForm from '../../BaseForm';
import { useState } from 'react';
import './styles.scss';
import { makeRequest } from 'core/utils/request';

type FormState = {
    name: string;
    price: string;
    category: string;
    description: string;
}

type FormEvent = HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement

const Form = () => {
    const [formData, setFormData] = useState<FormState>({
        name: '',
        price: '',
        category: '',
        description: ''
    });

    const handleOnChange = (event: React.ChangeEvent<FormEvent>) => {
        const name = event.target.name;
        const value = event.target.value;

        setFormData(data => ({ ...data, [name]: value}));
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement> ) => {
        event.preventDefault();

        const payload = {
            ...formData,
            imgUrl: 'https://images-submarino.b2w.io/produtos/01/00/img/93398/1/93398129_1GG.jpg',
            categories: [{ id: formData.category }]
        }

        makeRequest({ url: '/products', method: 'POST', data: payload}).then(() => {
            setFormData({ name: '', category: '', price: '', description: '' })
        });
    }

    return (
        <form onSubmit={handleSubmit}>
        <BaseForm title="CADASTRAR UM PRODUTO">
            <div className="row">
                <div className="col-6">
                    <input type="text" name="name" value={formData.name} placeholder="Nome" onChange={handleOnChange} className="form-control mb-3 mt-3"/>
                    <input type="text" name="price" value={formData.price} placeholder="Preço" onChange={handleOnChange} className="form-control"/>
                    <select name="category" value={formData.category} onChange={handleOnChange}className="form-control mt-3">
                        <option value="1">Livros</option>
                        <option value="3">Computadores</option>
                        <option value="2">Eletrônicos</option>
                    </select>
                </div>
                <div className="col-6">
                    <textarea name="description" value={formData.description} onChange={handleOnChange} cols={30} rows={10} className="form-control"/>
                </div>
            </div>
        </BaseForm>
        </form>
    )
}

export default Form;
