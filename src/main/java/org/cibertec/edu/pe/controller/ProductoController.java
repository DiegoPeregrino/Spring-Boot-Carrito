package org.cibertec.edu.pe.controller;

import java.util.ArrayList;
import java.util.List;

import org.cibertec.edu.pe.model.Detalle;
import org.cibertec.edu.pe.model.Producto;
import org.cibertec.edu.pe.model.Venta;
import org.cibertec.edu.pe.repository.IDetalleRepository;
import org.cibertec.edu.pe.repository.IProductoRepository;
import org.cibertec.edu.pe.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"carrito", "total"})
public class ProductoController {
    @Autowired
    private IProductoRepository productoRepository;
    
    @Autowired
    private IVentaRepository ventaRepository;
    
    @Autowired
    private IDetalleRepository detalleRepository;

    @GetMapping("/index")
    public String listado(Model model) {
        List<Producto> lista = productoRepository.findAll();
        model.addAttribute("productos", lista);
        return "index";
    }

    @GetMapping("/agregar/{idProducto}")
    public String agregar(Model model, @PathVariable(name = "idProducto", required = true) int idProducto,
                          @ModelAttribute("carrito") List<Detalle> carrito) {
        Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto != null) {
            Detalle detalle = new Detalle();
            detalle.setProducto(producto);
            detalle.setCantidad(1);
            detalle.setSubtotal(producto.getPrecio());
            carrito.add(detalle);
            model.addAttribute("carrito", carrito);
        }
        return "redirect:/index";
    }

    @GetMapping("/carrito")
    public String carrito() {
        return "carrito";
    }
    @GetMapping("/eliminar/{idProducto}")
    public String eliminarProducto(Model model, @PathVariable(name = "idProducto") int idProducto,
                                   @ModelAttribute("carrito") List<Detalle> carrito) {
        if (!carrito.isEmpty()) {
            for (int i = 0; i < carrito.size(); i++) {
                if (carrito.get(i).getProducto().getIdProducto() == idProducto) {
                    carrito.remove(i);
                    break;
                }
            }
            model.addAttribute("carrito", carrito);
        }
        return "redirect:/carrito";
    }

    @PostMapping("/actualizarCarrito")
    public String actualizarCarrito(Model model, @ModelAttribute("carrito") List<Detalle> carrito) {
        double subtotal = 0.0;
        for (Detalle detalle : carrito) {
            detalle.setSubtotal(detalle.getProducto().getPrecio() * detalle.getCantidad());
            subtotal += detalle.getSubtotal();
        }
        
        double descuento = subtotal * 0.25; // Descuento del 25%
        double totalSinEnvio = subtotal - descuento; // Subtotal sin el descuento
        double costoEnvio = totalSinEnvio * 0.05; // Costo de envío (5% del subtotal sin el descuento)
        double total = totalSinEnvio + costoEnvio; // Total con descuento y costo de envío
        
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("descuento", descuento);
        model.addAttribute("total", total);
        
        return "carrito";
    }

    @PostMapping("/pagar")
    public String pagar(Model model, @ModelAttribute("carrito") List<Detalle> carrito, @ModelAttribute("total") double total) {
        Venta venta = new Venta();
        venta.setMontoTotal(total);
        ventaRepository.save(venta);

        for (Detalle detalle : carrito) {
            detalle.setVenta(venta);
            detalleRepository.save(detalle);
        }

        carrito.clear();
        model.addAttribute("carrito", carrito);

        return "confirmacion_pago";
    }

    @ModelAttribute("carrito")
    public List<Detalle> getCarrito() {
        return new ArrayList<Detalle>();
    }
    
    @ModelAttribute("total")
    public double getTotal() {
        return 0.0;
    }
}


