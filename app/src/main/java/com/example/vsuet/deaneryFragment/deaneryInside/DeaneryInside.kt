package com.example.vsuet.deaneryFragment.deaneryInside

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vsuet.R
import com.example.vsuet.databinding.DeaneryInsideFragmentBinding
import org.java_websocket.client.WebSocketClient

class DeaneryInside : Fragment() {

    companion object {
        fun newInstance() = DeaneryInside()
    }

    val WS_PORT = 6524
    val WS_KEY = "$2a$12\$xSfeD98AHdU1FZkx7CvSEeOC9imjUEzlq28tOlDDhzLTfH7692LvS"
    val WS_EVENTS = mapOf(
        "GET_ORDER" to "getOrders",
        "CREATE_ORDER" to "createOrder",
        "UPDATE_ORDER_STATUS" to "updateOrderStatus",
        "BOT_SEND_ORDER" to "botSendOrder",
        "ERROR" to "error",
        "AUTH" to "auth"
    )
    private lateinit var binding: DeaneryInsideFragmentBinding
    private lateinit var webSocketClient: WebSocketClient
    private lateinit var viewModel: DeaneryInsideViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DeaneryInsideFragmentBinding.inflate(inflater)
        binding.orderButton.setOnClickListener {

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DeaneryInsideViewModel::class.java]
        // TODO: Use the ViewModel
    }

}